package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;

public record OrderItemsResponseDTO(
        Long id,
        int quantity,
        Double unitPrice,
        Double total,
        Double tvaAmount,
        Double totalWithTva,
        Long orderId,
        Long productId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
