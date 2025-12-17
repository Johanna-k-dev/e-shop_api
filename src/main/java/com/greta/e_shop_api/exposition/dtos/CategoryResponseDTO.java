package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;

public record CategoryResponseDTO(
        Long id,
        String label,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
