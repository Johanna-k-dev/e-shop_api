package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.persistence.entities.AddressEntity;
import com.greta.e_shop_api.persistence.entities.CustomerEntity;

public class CustomerRules {

    public static void validateBeforeCreation(CustomerEntity customer) {
        validateIdentity(customer);
    }

    public static void validateBeforeUpdate(CustomerEntity customer) {
        validateIdentity(customer);
    }

    private static void validateIdentity(CustomerEntity customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new RuntimeException("Le prénom est obligatoire.");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new RuntimeException("Le nom est obligatoire.");
        }
    }

    public static void validateAddressOwnership(AddressEntity address, CustomerEntity customer) {
        if (address.getCustomer() != null && address.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Cette adresse appartient déjà à un autre client.");
        }
    }
}
