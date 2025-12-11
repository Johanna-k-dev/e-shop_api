package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.CustomerRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import com.greta.e_shop_api.presistence.entities.CustomerEntity;

public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDTO dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());

        return entity;
    }

    public static CustomerResponseDTO toDto(CustomerEntity entity) {

        Long addressId = null;
        if (entity.getAddress() != null) {
            addressId = entity.getAddress().getId();
        }

        return new CustomerResponseDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                addressId,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

