package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.OrderItemsResponseDTO;
import com.greta.e_shop_api.presistence.entities.OrderItemsEntity;

public class OrderItemsMapper {

    public static OrderItemsResponseDTO toDto(OrderItemsEntity entity) {

        return new OrderItemsResponseDTO(
                entity.getId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotal(),
                entity.getTvaAmount(),
                entity.getTotalWithTva(),
                entity.getOrder() != null ? entity.getOrder().getId() : null,
                entity.getProduct() != null ? entity.getProduct().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()

        );
    }
}
