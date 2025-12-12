package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;

public class ProductCategoryRules {

    public static void validateBeforeCreation(boolean alreadyExists) {
        if (alreadyExists) {
            throw new RuntimeException("Ce produit est déjà associé à cette catégorie.");
        }
    }
}
