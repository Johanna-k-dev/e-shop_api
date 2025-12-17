package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.domain.rules.ProductRules;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.ProductRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductResponseDTO;
import com.greta.e_shop_api.mappers.ProductMapper;
import com.greta.e_shop_api.persistence.entities.ProductEntity;
import com.greta.e_shop_api.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Cacheable("products")
    public List<ProductEntity> getAllProducts() {
        System.out.println("🍣 Récupération depuis la base...");
        return productRepository.findAll();
    }


    @CacheEvict(value = "products", allEntries = true)
    public ProductEntity addProduct(ProductEntity product) {
        return productRepository.save(product);
    }


    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l’ID " + id + " n’existe pas."));
        return ProductMapper.toDto(product);
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        ProductEntity entity = ProductMapper.toEntity(dto);
        ProductRules.validateBeforeCreation(entity);
        ProductEntity saved = productRepository.save(entity);
        return ProductMapper.toDto(saved);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit " + id + " introuvable."));
        existing.updateFrom(dto);
        ProductRules.validateBeforeUpdate(existing);
        ProductEntity saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : produit " + id + " introuvable.");
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(ProductMapper::toDto)
                .toList();

    }
}
