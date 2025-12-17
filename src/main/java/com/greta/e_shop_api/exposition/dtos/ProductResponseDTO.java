package com.greta.e_shop_api.exposition.dtos;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        double price,
        int stock,
        double discount
) {}