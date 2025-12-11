package com.greta.e_shop_api.exposition.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CustomerRequestDTO(

        @NotBlank(message = "Le prénom ne peut pas être vide")
        String firstName,

        @NotBlank(message = "Le nom ne peut pas être vide")
        String lastName,

        @Positive(message = "L'identifiant de l'adresse doit être positif")
        Long addressId
) {}
