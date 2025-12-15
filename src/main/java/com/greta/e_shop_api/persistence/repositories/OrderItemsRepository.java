package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Long> {
}
