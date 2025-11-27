package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {



}
