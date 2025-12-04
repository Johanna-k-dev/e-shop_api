package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String firstName,
        String lastName,
        Long addressId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
