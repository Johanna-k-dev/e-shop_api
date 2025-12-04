package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.ProductRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductResponseDTO;
import com.greta.e_shop_api.presistence.entities.ProductEntity;



public class ProductMapper {
    // 👇 Convertit un ProductRequestDTO en ProductEntity
    public static ProductEntity toEntity(ProductRequestDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
        entity.setIsActive(dto.isActive() != null ? dto.isActive() : true);
        entity.setPrice(Double.valueOf(dto.price()));
        entity.setStock(dto.stock());
        entity.setDiscount(dto.discount());
        return entity;
    }

    // 👇 Convertit un ProductEntity en ProductResponseDTO
    public static ProductResponseDTO toDto(ProductEntity entity) {
        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getStock(),
                entity.getDiscount()
        );
    }
}
