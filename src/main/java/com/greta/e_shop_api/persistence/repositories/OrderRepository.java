package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
