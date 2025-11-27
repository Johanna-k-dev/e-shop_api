package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItemEntity, Long> {
}
