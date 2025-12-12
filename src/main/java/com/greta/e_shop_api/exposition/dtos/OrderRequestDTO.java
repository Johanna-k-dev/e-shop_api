package com.greta.e_shop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequestDTO(
        @NotBlank(message = "Le statut de la commande est obligatoire")
        String status,

        @NotNull(message = "L'identifiant de l'adresse est obligatoire")
        @Positive(message = "L'identifiant de l'adresse doit être positif")
        Long addressId,

        @NotNull(message = "Les items de la commande sont obligatoires")
        List<OrderItemsRequestDTO> items


) {}
