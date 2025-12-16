package com.greta.e_shop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequestDTO(
        @NotNull Long orderId,
        @Positive double amount,
        @NotBlank String paymentMethod
) {}
