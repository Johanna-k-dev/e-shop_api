package com.greta.e_shop_api.e2e;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greta.e_shop_api.e2e.config.TestContainerConfig;
import com.greta.e_shop_api.persistence.entities.AddressEntity;
import com.greta.e_shop_api.persistence.entities.CustomerEntity;
import com.greta.e_shop_api.persistence.entities.UserEntity;
import com.greta.e_shop_api.persistence.repositories.AddressRepository;
import com.greta.e_shop_api.persistence.repositories.CustomerRepository;
import com.greta.e_shop_api.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("e2e")
public class UserOrderPaymentE2ETest extends TestContainerConfig {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private AddressRepository addressRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    void shouldRegisterLoginAndCreatePaidOrder() throws Exception {


        String adminJwt = ensureAdminAndLogin();


        String createProductJson = """
        {
          "name": "Potion",
          "description": "Potion magique (test e2e)",
          "imageUrl": "https://example.com/potion.png",
          "isActive": true,
          "price": 50.0,
          "stock": 10,
          "discount": 0.0
        }
        """;

        String createdProductBody = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createProductJson))
                .andDo(r -> System.out.println("CREATE PRODUCT => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long productId = extractId(createdProductBody);


        String registerUserJson = """
        { "username":"john", "email":"john@example.com", "password":"secret123", "role":"USER" }
        """;

        int registerStatus = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerUserJson))
                .andDo(r -> System.out.println("REGISTER USER => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andReturn()
                .getResponse()
                .getStatus();

        if (registerStatus != 200 && registerStatus != 201 && registerStatus != 400) {
            throw new AssertionError("Register user status inattendu: " + registerStatus);
        }


        String tokenResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "email": "john@example.com", "password": "secret123" }
                        """))
                .andDo(r -> System.out.println("LOGIN USER => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String userJwt = extractToken(tokenResponse);


        Long customerId = ensureCustomerIdForEmail("john@example.com");
        Long addressId = ensureAddressIdForCustomer(customerId);


        String createOrderJson = """
        {
          "status": "CREATED",
          "addressId": %d,
          "items": [
            { "productId": 9, "quantity": 2, "unitPrice": 45.5 }
          ]
        }
        """.formatted(addressId, productId);

        String createdOrderBody = mockMvc.perform(post("/orders/customer/{customerId}", customerId)
                        .header("Authorization", "Bearer " + userJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderJson))
                .andDo(r -> System.out.println("CREATE ORDER => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value(100.0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long orderId = extractId(createdOrderBody);

        // 7) Pay order as USER
        String payJson = """
        {
          "orderId": %d,
          "amount": 100.0,
          "paymentMethod": "STRIPE_MOCK"
        }
        """.formatted(orderId);

        mockMvc.perform(post("/payment/checkout")
                        .header("Authorization", "Bearer " + userJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));


        mockMvc.perform(get("/orders/" + orderId)
                        .header("Authorization", "Bearer " + userJwt))
                .andDo(r -> System.out.println("GET ORDER => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].productId").value((int) productId));
    }

    private String ensureAdminAndLogin() throws Exception {
        if (!userRepository.existsByEmail("admin@example.com")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(UserEntity.Role.ADMIN);
            userRepository.save(admin);
        }

        String tokenResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "email": "admin@example.com", "password": "admin123" }
                        """))
                .andDo(r -> System.out.println("LOGIN ADMIN => " + r.getResponse().getStatus()
                        + " " + r.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return extractToken(tokenResponse);
    }

    private Long ensureCustomerIdForEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User introuvable: " + email));

        return customerRepository.findByUserId(user.getId())
                .map(CustomerEntity::getId)
                .orElseGet(() -> {
                    CustomerEntity c = new CustomerEntity();
                    c.setFirstName("John");
                    c.setLastName("Doe");
                    c.setUser(user);
                    return customerRepository.save(c).getId();
                });
    }

    private Long ensureAddressIdForCustomer(Long customerId) {

        return addressRepository.findFirstByCustomer_Id(customerId)
                .map(AddressEntity::getId)
                .orElseGet(() -> {
                    CustomerEntity customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalStateException("Customer introuvable: " + customerId));

                    AddressEntity a = new AddressEntity();

                    // ⚠️ adapte ici selon les VRAIS champs de AddressEntity
                    a.setStreet("1 rue des Tests");
                    a.setCity("Paris");
                    a.setZipCode("75001");
                    a.setCounty("France");
                    a.setCustomer(customer);

                    return addressRepository.save(a).getId();
                });
    }


    private String extractToken(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        if (root.has("token")) return root.get("token").asText();
        if (root.has("accessToken")) return root.get("accessToken").asText();
        throw new IllegalStateException("Token field not found in login response: " + json);
    }

    private long extractId(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        if (root.has("id")) return root.get("id").asLong();
        if (root.has("data") && root.get("data").has("id")) return root.get("data").get("id").asLong();
        throw new IllegalStateException("No 'id' field found in response: " + json);
    }
}
