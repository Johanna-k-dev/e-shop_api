package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    List<FavoriteEntity> findByCustomer_Id(Long customerId);

    Optional<FavoriteEntity> findByCustomer_IdAndProduct_Id(Long customerId, Long productId);
}
