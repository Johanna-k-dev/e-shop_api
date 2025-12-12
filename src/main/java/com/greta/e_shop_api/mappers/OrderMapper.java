package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import com.greta.e_shop_api.exposition.dtos.OrderItemsResponseDTO;
import com.greta.e_shop_api.exposition.dtos.OrderResponseDTO;
import com.greta.e_shop_api.presistence.entities.OrderEntity;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toDto(OrderEntity entity) {


        List<Long> addressIds = List.of(entity.getAddress().getId());


        CustomerResponseDTO customerDto = new CustomerResponseDTO(
                entity.getCustomer().getId(),
                entity.getCustomer().getFirstName(),
                entity.getCustomer().getLastName(),
                addressIds,
                entity.getCustomer().getCreatedAt(),
                entity.getCustomer().getUpdatedAt()
        );

        List<OrderItemsResponseDTO> items = entity.getItems() != null
                ? entity.getItems().stream()
                .map(OrderItemsMapper::toDto)
                .toList()
                : List.of();

        return new OrderResponseDTO(
                entity.getId(),
                entity.getStatus(),
                customerDto,
                entity.getTotal(),
                entity.getTvaAmount(),
                entity.getTotalWithTva(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                items
        );
    }
}

