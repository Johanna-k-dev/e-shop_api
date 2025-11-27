package com.greta.e_shop_api.exposition.controllers;


import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;
import com.greta.e_shop_api.presistence.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @GetMapping
    private List<ProductCategoryEntity> getAllProductCategories() {
        List<ProductCategoryEntity> productCategories = productCategoryRepository.findAll();
        return productCategories;
    }
}
