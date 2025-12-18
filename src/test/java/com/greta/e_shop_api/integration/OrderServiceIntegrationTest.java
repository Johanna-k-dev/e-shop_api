package com.greta.e_shop_api.integration;


import com.greta.e_shop_api.domain.services.OrderService;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.OrderItemsRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderResponseDTO;
import com.greta.e_shop_api.persistence.entities.AddressEntity;
import com.greta.e_shop_api.persistence.entities.CustomerEntity;
import com.greta.e_shop_api.persistence.entities.ProductEntity;
import com.greta.e_shop_api.persistence.repositories.AddressRepository;
import com.greta.e_shop_api.persistence.repositories.CustomerRepository;
import com.greta.e_shop_api.persistence.repositories.OrderRepository;
import com.greta.e_shop_api.persistence.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:eshop_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never"
})
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired
    OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired CustomerRepository customerRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired ProductRepository productRepository;


    private CustomerEntity customer;
    private AddressEntity address;
    private ProductEntity product1;
    private ProductEntity product2;

    @BeforeEach
    void setup() {
        // --- CUSTOMER ---
        customer = new CustomerEntity();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer = customerRepository.save(customer);

        // --- ADDRESS (rattachée au customer) ---
        this.address = new AddressEntity();
        this.address.setCity("Tours");
        this.address.setCounty("Indre-et-Loire");
        this.address.setStreet("1 rue de la Paix");
        this.address.setZipCode("37000");
        this.address.setCreatedAt(LocalDateTime.now());
        this.address.setUpdatedAt(LocalDateTime.now());
        this.address.setCustomer(customer);
        this.address = addressRepository.save(this.address);

        // --- PRODUCTS ---
        product1 = new ProductEntity();
        product1.setName("PS5");
        product1.setDescription("Console Sony PlayStation 5");
        product1.setPrice(500.0);
        product1.setStock(10);
        product1.setIsActive(true);
        product1.setDiscount(0.0);
        product1.setImageUrl("ps5.png");
        product1 = productRepository.save(product1);

        product2 = new ProductEntity();
        product2.setName("Manette");
        product2.setDescription("Manette DualSense PS5");
        product2.setPrice(70.0);
        product2.setStock(50);
        product2.setIsActive(true);
        product2.setDiscount(0.0);
        product2.setImageUrl("dualsense.png");
        product2 = productRepository.save(product2);
    }

    @Test
    void create_shouldPersistOrderAndItems_andComputeTotals() {
        // Given
        var dto = new OrderRequestDTO(
                address.getId(),
                "PENDING",
                List.of(
                        new OrderItemsRequestDTO(2, product1.getPrice(), product1.getId()),
                        new OrderItemsRequestDTO(1, product2.getPrice(), product2.getId())
                )
        );




        OrderResponseDTO created = orderService.create(customer.getId(), dto);

        assertThat(created).isNotNull();
        assertThat(created.id()).isNotNull();
        assertThat(created.items()).hasSize(2);


        assertThat(created.total()).isCloseTo(1070.0, within(0.0001));
        assertThat(created.tvaAmount()).isCloseTo(214.0, within(0.0001));
        assertThat(created.totalWithTva()).isCloseTo(1284.0, within(0.0001));


        var saved = orderRepository.findById(created.id()).orElseThrow();
        assertThat(saved.getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(saved.getAddress().getId()).isEqualTo(address.getId());
        assertThat(saved.getItems()).hasSize(2);


        var items = saved.getItems();
        assertThat(items.stream().mapToInt(i -> i.getQuantity()).sum()).isEqualTo(3);
        assertThat(saved.getTotalWithTva()).isCloseTo(1284.0, within(0.0001));
    }

    @Test
    void create_shouldThrow_whenCustomerNotFound() {
        var dto = new OrderRequestDTO(
                address.getId(),
                "PENDING",
                List.of(new OrderItemsRequestDTO(1, null, 999999L)) // ✅ produit inexistant
        );

        assertThatThrownBy(() -> orderService.create(customer.getId(), dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Produit introuvable");

    }

    @Test
    void create_shouldThrow_whenAddressNotBelongToCustomer() {
        // autre client
        CustomerEntity other = new CustomerEntity();
        other.setFirstName("Jane");
        other.setLastName("Smith");
        other.setCreatedAt(LocalDateTime.now());
        other.setUpdatedAt(LocalDateTime.now());
        other = customerRepository.save(other);

        // autre adresse (obligatoire: city + le reste si tes colonnes sont NOT NULL)
        AddressEntity otherAddress = new AddressEntity();
        otherAddress.setCity("Paris");
        otherAddress.setCounty("Ile-de-France");
        otherAddress.setStreet("10 rue test");
        otherAddress.setZipCode("75000");
        otherAddress.setCreatedAt(LocalDateTime.now());
        otherAddress.setUpdatedAt(LocalDateTime.now());
        otherAddress.setCustomer(other);
        otherAddress = addressRepository.save(otherAddress); // <- ici plus de city null

        var dto = new OrderRequestDTO(
                otherAddress.getId(),
                "PENDING",
                List.of(new OrderItemsRequestDTO(1, null, product2.getId()))
        );

        assertThatThrownBy(() -> orderService.create(customer.getId(), dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("n'appartient pas");
    }

    @Test
    void create_shouldThrow_whenProductNotFound() {
        var dto = new OrderRequestDTO(
                address.getId(),
                "PENDING",
                List.of(new OrderItemsRequestDTO(1, null, 999999L)) // produit inexistant
        );

        assertThatThrownBy(() -> orderService.create(customer.getId(), dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Produit introuvable");
    }
}
