package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.CustomerRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import com.greta.e_shop_api.persistence.entities.CustomerEntity;

import java.util.List;

public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDTO dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        return entity;
    }

    public static CustomerResponseDTO toDto(CustomerEntity entity) {

        List<Long> addressIds = entity.getAddresses() != null
                ? entity.getAddresses().stream().map(a -> a.getId()).toList()
                : List.of();

        return new CustomerResponseDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                addressIds,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

