package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Long> {
}
