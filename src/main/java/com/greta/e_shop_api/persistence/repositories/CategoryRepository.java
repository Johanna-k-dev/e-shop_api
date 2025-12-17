package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByLabelContainingIgnoreCase(String keyword);
}
