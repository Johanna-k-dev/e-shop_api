package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.OrderItemsService;
import com.greta.e_shop_api.exposition.dtos.OrderItemsRequestDTO;
import com.greta.e_shop_api.exposition.dtos.OrderItemsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemsResponseDTO> create(@Valid @RequestBody OrderItemsRequestDTO dto) {
        return ResponseEntity.ok(orderItemService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrderItemsResponseDTO>> getAll() {
        return ResponseEntity.ok(orderItemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemsResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderItemsRequestDTO dto
    ) {
        return ResponseEntity.ok(orderItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
