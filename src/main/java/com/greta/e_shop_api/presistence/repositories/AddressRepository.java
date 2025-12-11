package com.greta.e_shop_api.presistence.repositories;

import com.greta.e_shop_api.presistence.entities.AddressEntity;
import com.greta.e_shop_api.presistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
  List<AddressEntity> findByCityContainingIgnoreCase(String keyword);
}
