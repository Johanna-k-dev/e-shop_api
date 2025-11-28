package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.presistence.entities.ProductEntity;

public class ProductRules {
    public static void validateBeforeCreation(ProductEntity product) {
        if (product.getPrice() <= 0) {
            throw new RuntimeException("Le prix doit être supérieur à 0.");
        }
        if (product.getStock() < 0) {
            throw new RuntimeException("Le stock ne peut pas être négatif.");
        }
    }

    public static void validateBeforeUpdate(ProductEntity product) {
        if (product.getPrice() > 10000) {
            throw new RuntimeException("Le prix dépasse la limite autorisée.");
        }
    }
}
