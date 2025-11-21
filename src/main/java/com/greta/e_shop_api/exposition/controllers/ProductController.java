package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    private List<ProductEntity> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products;
    }

    @GetMapping("/search")
    public List<ProductEntity> searchProducts(@RequestParam String keyword) {
        List<ProductEntity> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products;
    }

}
