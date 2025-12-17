package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

    List<ProductEntity> Id(long id);

    Page<ProductEntity> findAll(Pageable pageable);
}
