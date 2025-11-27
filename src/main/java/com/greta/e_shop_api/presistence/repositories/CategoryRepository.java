package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByLabelContainingIgnoreCase(String keyword);
}
