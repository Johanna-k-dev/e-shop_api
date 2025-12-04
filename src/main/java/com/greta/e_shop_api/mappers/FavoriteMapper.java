package com.greta.e_shop_api.mappers;

import com.greta.e_shop_api.exposition.dtos.FavoriteResponseDTO;
import com.greta.e_shop_api.presistence.entities.FavoriteEntity;

public class FavoriteMapper {

    public static FavoriteResponseDTO toDto(FavoriteEntity entity) {
        return new FavoriteResponseDTO(
                entity.getId(),
                entity.getCustomer().getId(),
                entity.getProduct().getId()
        );
    }
}

