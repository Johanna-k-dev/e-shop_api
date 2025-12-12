package com.greta.e_shop_api.exposition.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemsRequestDTO(
        @NotNull
        @Positive(message = "La quantité doit être positive")
        int quantity,

        @NotNull(message = "Le prix unitaire est obligatoire")
        @PositiveOrZero(message = "Le prix unitaire ne peut pas être négatif")
        Double unitPrice,

        @NotNull(message = "L'identifiant de la commande est obligatoire")
        @Positive(message = "L'identifiant de la commande doit être positif")
        Long orderId,

        @NotNull(message = "L'identifiant du produit est obligatoire")
        @Positive(message = "L'identifiant du produit doit être positif")
        Long productId

) {}
