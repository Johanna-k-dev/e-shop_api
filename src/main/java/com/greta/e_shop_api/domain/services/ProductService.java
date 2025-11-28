package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.domain.rules.ProductRules;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.ProductRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductResponseDTO;
import com.greta.e_shop_api.mappers.ProductMapper;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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
}
