package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.exposition.dtos.ProductRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductResponseDTO;
import com.greta.e_shop_api.mappers.ProductMapper;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> dtos = productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String keyword) {
        List<ProductResponseDTO> dtos = productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO request
    ) {
        ProductEntity entity = ProductMapper.toEntity(request);
        ProductEntity saved = productRepository.save(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        ProductEntity existing = productRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(request.name());
            existing.setDescription(request.description());
            existing.setImageUrl(request.imageUrl());
            existing.setIsActive(request.isActive() != null ? request.isActive() : existing.getIsActive());
            existing.setPrice(request.price());
            existing.setStock(request.stock());
            existing.setDiscount(request.discount());

            ProductEntity updated = productRepository.save(existing);
            return ResponseEntity.ok(ProductMapper.toDto(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
