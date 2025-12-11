package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;

public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        int zipCode,
        String county,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
