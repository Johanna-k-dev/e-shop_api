package com.greta.e_shop_api.domain.rules;

public class ProductCategoryRules {

    public static void validateBeforeCreation(boolean alreadyExists) {
        if (alreadyExists) {
            throw new RuntimeException("Ce produit est déjà associé à cette catégorie.");
        }
    }
}
