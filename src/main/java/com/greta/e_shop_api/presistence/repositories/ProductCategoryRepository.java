package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    List<ProductCategoryEntity> findByProduct_Id(Long productId);

    List<ProductCategoryEntity> findByCategory_Id(Long categoryId);
}

