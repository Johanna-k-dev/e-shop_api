package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.OrderService;
import com.greta.e_shop_api.exposition.dtos.OrderRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<OrderResponseDTO> create(
            @PathVariable Long customerId,
            @Valid @RequestBody OrderRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(customerId, dto));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PutMapping("/{orderId}/customer/{customerId}")
    public OrderResponseDTO update(
            @PathVariable Long orderId,
            @PathVariable Long customerId,
            @Valid @RequestBody OrderRequestDTO dto
    ) {
        return orderService.update(orderId, customerId, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
