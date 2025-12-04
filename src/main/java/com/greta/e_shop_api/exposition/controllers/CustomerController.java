package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.CustomerService;
import com.greta.e_shop_api.exposition.dtos.CustomerRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(customerService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO dto
    ) {
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(customerService.searchByLastName(keyword));
    }
}
