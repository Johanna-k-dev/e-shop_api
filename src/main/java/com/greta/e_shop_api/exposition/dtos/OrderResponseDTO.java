package com.greta.e_shop_api.exposition.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String status,
        CustomerResponseDTO customer,
        Double total,
        Double tvaAmount,
        Double totalWithTva,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<OrderItemsResponseDTO> items
) {}
