package com.greta.e_shop_api.exposition.dtos;

import jakarta.validation.constraints.*;

public record AddressRequestDTO(

        @NotBlank(message = "La rue ne peut pas être vide")
        @Size(max = 120, message = "La rue ne doit pas dépasser 120 caractères")
        String street,

        @NotBlank(message = "La ville ne peut pas être vide")
        @Size(max = 80, message = "La ville ne doit pas dépasser 80 caractères")
        String city,

        @NotNull(message = "Le code postal est obligatoire")
        @Min(value = 1, message = "Le code postal doit être valide")
        String zipCode,

        @NotBlank(message = "Le département (county) est obligatoire")
        String county
) {}
