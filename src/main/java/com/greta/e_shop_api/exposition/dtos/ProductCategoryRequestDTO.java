package com.greta.e_shop_api.exposition.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCategoryRequestDTO(

        @NotNull(message = "L'identifiant du produit est obligatoire")
        @Positive(message = "L'identifiant du produit doit être positif")
        Long productId,

        @NotNull(message = "L'identifiant de la catégorie est obligatoire")
        @Positive(message = "L'identifiant de la catégorie doit être positif")
        Long categoryId
) {}