package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.AddressRequestDTO;
import com.greta.e_shop_api.exposition.dtos.AddressResponseDTO;
import com.greta.e_shop_api.presistence.entities.AddressEntity;

public class AddressMapper {

    public static AddressEntity toEntity(AddressRequestDTO dto) {
        AddressEntity entity = new AddressEntity();
        entity.setStreet(dto.street());
        entity.setCity(dto.city());
        entity.setZipCode(dto.zipCode());
        entity.setCounty(dto.county());
        return entity;
    }

    public static AddressResponseDTO toDto(AddressEntity entity) {
        return new AddressResponseDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getCounty(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
