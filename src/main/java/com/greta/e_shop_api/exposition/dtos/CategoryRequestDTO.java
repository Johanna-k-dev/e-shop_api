package com.greta.e_shop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
        @Size(max = 80, message = "Le label ne doit pas dépasser 80 caractères")
        String label
) {}
