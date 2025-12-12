package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponseDTO(
        Long id,
        String firstName,
        String lastName,
        List<Long> addressIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}


