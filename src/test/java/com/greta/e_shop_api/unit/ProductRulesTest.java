package com.greta.e_shop_api.unit;

import com.greta.e_shop_api.domain.rules.ProductRules;
import com.greta.e_shop_api.persistence.entities.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRulesTest {
    private ProductEntity activeProduct(double price) {
        ProductEntity p = new ProductEntity();
        p.setIsActive(true);
        p.setPrice(price);
        return p;
    }

    private ProductEntity inactiveProduct(double price) {
        ProductEntity p = new ProductEntity();
        p.setIsActive(false);
        p.setPrice(price);
        return p;
    }

    @Test
    @DisplayName("shouldThrowIfDiscountAbove100")
    void shouldThrowIfDiscountAbove100() {
        ProductEntity p = activeProduct(100);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                ProductRules.validatePromotion(
                        p,
                        101,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                )
        );

        assertEquals("La réduction doit être comprise entre 0 et 100.", ex.getMessage());
    }

    @Test
    @DisplayName("shouldThrowIfDiscountNegative")
    void shouldThrowIfDiscountNegative() {
        ProductEntity p = activeProduct(100);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                ProductRules.validatePromotion(
                        p,
                        -1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                )
        );

        assertEquals("La réduction doit être comprise entre 0 et 100.", ex.getMessage());
    }

    @Test
    @DisplayName("shouldThrowIfDiscountedPriceNegative")
    void shouldThrowIfDiscountedPriceNegative() {
        // Cas "donnée corrompue": prix négatif -> prix remisé négatif
        ProductEntity p = activeProduct(-10);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                ProductRules.validatePromotion(
                        p,
                        10,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                )
        );

        assertEquals("Le prix remisé ne peut pas être négatif.", ex.getMessage());
    }

    @Test
    @DisplayName("shouldThrowIfInactiveProductHasDiscount")
    void shouldThrowIfInactiveProductHasDiscount() {
        ProductEntity p = inactiveProduct(100);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                ProductRules.validatePromotion(
                        p,
                        10,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                )
        );

        assertEquals("Un produit inactif ne peut pas avoir de promotion.", ex.getMessage());
    }

    @Test
    @DisplayName("shouldThrowIfPromoExpired")
    void shouldThrowIfPromoExpired() {
        LocalDate today = LocalDate.of(2025, 12, 14);
        ProductEntity p = activeProduct(100);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                ProductRules.validatePromotion(
                        p,
                        10,
                        today.minusDays(10),
                        today.minusDays(1), // endDate < today
                        today
                )
        );

        assertEquals("La promotion est expirée.", ex.getMessage());
    }
}
