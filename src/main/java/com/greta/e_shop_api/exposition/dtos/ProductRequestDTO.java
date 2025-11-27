package com.greta.e_shop_api.exposition.dtos;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        String imageUrl,
        Boolean isActive,
        BigDecimal price,
        int stock,
        double discount
) {}
