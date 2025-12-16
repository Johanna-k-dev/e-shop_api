package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
  List<AddressEntity> findByCityContainingIgnoreCase(String keyword);
    Optional<AddressEntity> findFirstByCustomer_Id(Long customerId);
}
