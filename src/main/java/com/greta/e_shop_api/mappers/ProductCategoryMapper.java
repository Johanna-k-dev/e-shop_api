package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.ProductCategoryResponseDTO;
import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;

public class ProductCategoryMapper {

    public static ProductCategoryResponseDTO toDto(ProductCategoryEntity entity) {
        return new ProductCategoryResponseDTO(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getCategory().getId()
        );
    }
}
