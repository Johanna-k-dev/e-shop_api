package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.domain.rules.OrderRules;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.OrderItemsRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderResponseDTO;
import com.greta.e_shop_api.mappers.OrderMapper;
import com.greta.e_shop_api.presistence.entities.*;
import com.greta.e_shop_api.presistence.repositories.AddressRepository;
import com.greta.e_shop_api.presistence.repositories.CustomerRepository;
import com.greta.e_shop_api.presistence.repositories.OrderRepository;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public OrderResponseDTO create(Long customerId, OrderRequestDTO dto) {

        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + customerId));

        AddressEntity address = addressRepository.findById(dto.addressId())
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + dto.addressId()));

        if (address.getCustomer() == null || address.getCustomer().getId() != customer.getId()) {
            throw new IllegalArgumentException("Cette adresse n'appartient pas à ce client.");
        }

        OrderEntity order = new OrderEntity();
        order.setStatus(dto.status());
        order.setCustomer(customer);
        order.setAddress(address);

        if (order.getItems() == null) order.setItems(new ArrayList<>());

        double orderTotalHT = 0.0;
        double orderTva = 0.0;
        double orderTTC = 0.0;
        final double TVA_RATE = 0.2;

        for (OrderItemsRequestDTO itemDto : dto.items()) {

            ProductEntity product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + itemDto.productId()));

            OrderItemsEntity item = new OrderItemsEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.quantity());
            item.setUnitPrice(product.getPrice());

            double totalHT = item.getQuantity() * item.getUnitPrice();
            double tvaAmount = totalHT * TVA_RATE;
            double totalTTC = totalHT + tvaAmount;

            item.setTotal(totalHT);
            item.setTvaAmount(tvaAmount);
            item.setTotalWithTva(totalTTC);

            order.getItems().add(item);

            orderTotalHT += totalHT;
            orderTva += tvaAmount;
            orderTTC += totalTTC;
        }

        order.setTotal(orderTotalHT);
        order.setTvaAmount(orderTva);
        order.setTotalWithTva(orderTTC);

        OrderRules.validateBeforeCreation(order);

        OrderEntity saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderResponseDTO getById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + id));

        return OrderMapper.toDto(entity);
    }

    public OrderResponseDTO update(Long orderId, Long customerId, OrderRequestDTO dto) {

        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + orderId));

        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + customerId));

        AddressEntity address = addressRepository.findById(dto.addressId())
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + dto.addressId()));

        if (address.getCustomer() == null || address.getCustomer().getId() != customer.getId()) {
            throw new IllegalArgumentException("Cette adresse n'appartient pas à ce client.");
        }

        OrderRules.validateBeforeStatusChange(entity, dto.status());

        entity.setStatus(dto.status());
        entity.setCustomer(customer);
        entity.setAddress(address);

        return OrderMapper.toDto(orderRepository.save(entity));
    }

    public void delete(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + id));

        OrderRules.validateBeforeDeletion(entity);

        orderRepository.delete(entity);
    }
}
