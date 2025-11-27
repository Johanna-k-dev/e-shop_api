package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
