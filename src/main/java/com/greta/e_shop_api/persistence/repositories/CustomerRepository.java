package com.greta.e_shop_api.persistence.repositories;

import com.greta.e_shop_api.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    List<CustomerEntity> findByLastNameContainingIgnoreCase(String keyword);

    Optional<CustomerEntity> findByUserId(Long userId);

    Optional<CustomerEntity> findByUserEmail(String email);
}
