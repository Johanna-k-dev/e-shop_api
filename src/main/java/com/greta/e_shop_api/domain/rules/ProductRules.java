package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.persistence.entities.ProductEntity;

import java.time.LocalDate;

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

    public static void validatePromotion(
            ProductEntity product,
            double discount,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate today
    ) {
        if (discount < 0 || discount > 100) {
            throw new RuntimeException("La réduction doit être comprise entre 0 et 100.");
        }

        if (Boolean.FALSE.equals(product.getIsActive()) && discount > 0) {
            throw new RuntimeException("Un produit inactif ne peut pas avoir de promotion.");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Les dates de promotion sont invalides.");
        }

        if (endDate.isBefore(today)) {
            throw new RuntimeException("La promotion est expirée.");
        }

        double discountedPrice = product.getPrice() * (1 - discount / 100);
        if (discountedPrice < 0) {
            throw new RuntimeException("Le prix remisé ne peut pas être négatif.");
        }
    }
}
