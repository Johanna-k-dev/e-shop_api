package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    List<ProductCategoryEntity> findByProduct_Id(Long productId);

    List<ProductCategoryEntity> findByCategory_Id(Long categoryId);

    Optional<ProductCategoryEntity> findByProduct_IdAndCategory_Id(Long productId, Long categoryId);



}

