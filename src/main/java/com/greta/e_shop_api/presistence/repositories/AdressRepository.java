package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<AddressEntity, Long> {
}
