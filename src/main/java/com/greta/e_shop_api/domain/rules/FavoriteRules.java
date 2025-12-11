package com.greta.e_shop_api.domain.rules;

public class FavoriteRules {
    public static void validateNotDuplicated(boolean alreadyExists) {
        if (alreadyExists) {
            throw new RuntimeException("Ce produit est déjà dans les favoris du client.");
        }
    }
}
