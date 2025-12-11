package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.presistence.entities.OrderItemsEntity;

public class OrderItemsRules {

    public static void validateBeforeCreation(OrderItemsEntity item) {
        if (item.getQuantity() <= 0) {
            throw new RuntimeException("La quantité doit être supérieure à 0.");
        }
        if (item.getUnitPrice() < 0) {
            throw new RuntimeException("Le prix unitaire ne peut pas être négatif.");
        }
        if (item.getProduct() != null && Boolean.FALSE.equals(item.getProduct().getIsActive())) {
            throw new RuntimeException("On ne peut pas ajouter au panier un produit inactif.");
        }
    }
}
