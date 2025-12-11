package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.presistence.entities.CustomerEntity;

public class CustomerRules {

    public static void validateBeforeCreation(CustomerEntity customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new RuntimeException("Le prénom est obligatoire.");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new RuntimeException("Le nom est obligatoire.");
        }
    }
}
