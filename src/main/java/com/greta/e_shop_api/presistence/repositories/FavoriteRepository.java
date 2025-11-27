package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
}
