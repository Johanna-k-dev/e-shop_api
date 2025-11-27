package com.greta.e_shop_api.exposition.controllers;


import com.greta.e_shop_api.presistence.entities.CategoryEntity;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories;
    }

    @GetMapping("/search")
    public List<CategoryEntity> searchCategories(@RequestParam String keyword) {
        List<CategoryEntity> categories = categoryRepository.findByLabelContainingIgnoreCase(keyword);
        return categories;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            return ResponseEntity.ok(category); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity category) {
        CategoryEntity saved = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long id, @RequestBody CategoryEntity newData) {

        CategoryEntity existing = categoryRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setLabel(newData.getLabel());
            CategoryEntity updated= categoryRepository.save(existing);
            return ResponseEntity.ok(updated);
        }else {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

}
