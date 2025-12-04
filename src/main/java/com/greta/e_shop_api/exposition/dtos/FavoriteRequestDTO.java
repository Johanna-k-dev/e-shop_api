package com.greta.e_shop_api.exposition.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record  FavoriteRequestDTO (
        @NotNull(message = "L’identifiant du client est obligatoire")
        @Positive(message = "L’identifiant du client doit être positif")
        Long customerId,

        @NotNull(message = "L’identifiant du produit est obligatoire")
        @Positive(message = "L’identifiant du produit doit être positif")
        Long productId
) {}
