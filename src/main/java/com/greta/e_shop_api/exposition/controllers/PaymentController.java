package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.PaymentService;
import com.greta.e_shop_api.exposition.dtos.PaymentRequestDTO;
import com.greta.e_shop_api.exposition.dtos.PaymentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> checkout(
            @Valid @RequestBody PaymentRequestDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(paymentService.checkout(dto, authentication));
    }
}
