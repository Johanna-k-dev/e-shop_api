package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.CategoryRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CategoryResponseDTO;
import com.greta.e_shop_api.persistence.entities.CategoryEntity;

public class CategoryMapper {


    public static CategoryEntity toEntity(CategoryRequestDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setLabel(dto.label());

        return entity;
    }

    public static CategoryResponseDTO toDto(CategoryEntity entity) {
        return new CategoryResponseDTO(
                entity.getId(),
                entity.getLabel(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}