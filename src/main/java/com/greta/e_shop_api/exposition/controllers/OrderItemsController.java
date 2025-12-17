package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.OrderItemsService;
import com.greta.e_shop_api.domain.services.OrderService;
import com.greta.e_shop_api.exposition.dtos.OrderItemsRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderItemsResponseDTO;
import com.greta.e_shop_api.exposition.dtos.OrderRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;
    private OrderService orderService;

    @PostMapping("/orders/{orderId}/items")
    public ResponseEntity<OrderResponseDTO> create(
            @PathVariable Long customerId,
            @Valid @RequestBody OrderRequestDTO dto
    ) {
        return ResponseEntity.ok(orderService.create(customerId, dto));
    }


    @GetMapping
    public ResponseEntity<List<OrderItemsResponseDTO>> getAll() {
        return ResponseEntity.ok(orderItemsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemsService.getById(id));
    }

    @PutMapping("/orders/{orderId}/items/{itemId}")
    public ResponseEntity<OrderItemsResponseDTO> update(
            @PathVariable Long orderId,
            @PathVariable Long itemId,

            @Valid @RequestBody OrderItemsRequestDTO dto
    ) {
        return ResponseEntity.ok(orderItemsService.update(itemId, orderId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
