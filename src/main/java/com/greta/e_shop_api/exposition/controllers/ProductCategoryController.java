package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.ProductCategoryService;
import com.greta.e_shop_api.exposition.dtos.ProductCategoryRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;



    @GetMapping
    public ResponseEntity<List<ProductCategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productCategoryService.getById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCategoryResponseDTO>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productCategoryService.findByProduct(productId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductCategoryResponseDTO>> findByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productCategoryService.findByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductCategoryResponseDTO> create(@Valid @RequestBody ProductCategoryRequestDTO dto) {
        return ResponseEntity.ok(productCategoryService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
