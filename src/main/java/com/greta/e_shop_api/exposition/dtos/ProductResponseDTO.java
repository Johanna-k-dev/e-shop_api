package com.greta.e_shop_api.exposition.dtos;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        int stock,
        double discount
) {}