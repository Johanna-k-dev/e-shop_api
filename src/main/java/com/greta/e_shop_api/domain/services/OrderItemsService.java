package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.domain.rules.OrderItemsRules;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.OrderItemsRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderItemsResponseDTO;
import com.greta.e_shop_api.mappers.OrderItemsMapper;
import com.greta.e_shop_api.presistence.entities.OrderEntity;
import com.greta.e_shop_api.presistence.entities.OrderItemsEntity;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.OrderItemsRepository;
import com.greta.e_shop_api.presistence.repositories.OrderRepository;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemsRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private static final double TVA_RATE = 0.2;

    public OrderItemsResponseDTO create(OrderItemsRequestDTO dto) {

        OrderEntity order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + dto.orderId()));

        ProductEntity product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + dto.productId()));

        OrderItemsEntity entity = new OrderItemsEntity();
        entity.setQuantity(dto.quantity());
        entity.setUnitPrice(dto.unitPrice());
        entity.setOrder(order);
        entity.setProduct(product);

        OrderItemsRules.validateBeforeCreation(entity);

        double totalHT = dto.quantity() * dto.unitPrice();
        double tvaAmount = totalHT * TVA_RATE;
        double totalTTC = totalHT + tvaAmount;

        entity.setTotal(totalHT);
        entity.setTvaAmount(tvaAmount);
        entity.setTotalWithTva(totalTTC);

        return OrderItemsMapper.toDto(orderItemRepository.save(entity));
    }

    public List<OrderItemsResponseDTO> getAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(OrderItemsMapper::toDto)
                .toList();
    }

    public OrderItemsResponseDTO getById(Long id) {
        OrderItemsEntity entity = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item introuvable : " + id));

        return OrderItemsMapper.toDto(entity);
    }

    public OrderItemsResponseDTO update(Long id, OrderItemsRequestDTO dto) {

        OrderItemsEntity entity = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item introuvable : " + id));

        OrderEntity order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable : " + dto.orderId()));

        ProductEntity product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + dto.productId()));

        entity.setQuantity(dto.quantity());
        entity.setUnitPrice(dto.unitPrice());
        entity.setOrder(order);
        entity.setProduct(product);

        OrderItemsRules.validateBeforeCreation(entity);

        double totalHT = dto.quantity() * dto.unitPrice();
        double tvaAmount = totalHT * TVA_RATE;
        double totalTTC = totalHT + tvaAmount;

        entity.setTotal(totalHT);
        entity.setTvaAmount(tvaAmount);
        entity.setTotalWithTva(totalTTC);

        return OrderItemsMapper.toDto(orderItemRepository.save(entity));
    }

    public void delete(Long id) {
        OrderItemsEntity entity = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item introuvable : " + id));

        orderItemRepository.delete(entity);
    }
}
