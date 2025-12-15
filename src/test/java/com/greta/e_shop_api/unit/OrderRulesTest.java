package com.greta.e_shop_api.unit;

import com.greta.e_shop_api.domain.rules.OrderItemsRules;
import com.greta.e_shop_api.persistence.entities.OrderItemsEntity;
import com.greta.e_shop_api.persistence.entities.ProductEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRulesTest {

    @Test
    @DisplayName("OK - validation passe quand les données sont valides")
    void should_pass_validation_when_item_is_valid() {
        // GIVEN
        ProductEntity product = new ProductEntity();
        product.setIsActive(true);

        OrderItemsEntity item = new OrderItemsEntity();
        item.setQuantity(2);
        item.setUnitPrice(19.99);
        item.setProduct(product);

        // WHEN / THEN
        assertDoesNotThrow(() ->
                OrderItemsRules.validateBeforeCreation(item)
        );
    }

    @Test
    @DisplayName("ERREUR - quantité <= 0")
    void should_throw_exception_when_quantity_is_zero_or_negative() {
        // GIVEN
        OrderItemsEntity item = new OrderItemsEntity();
        item.setQuantity(0);
        item.setUnitPrice(10.0);

        // WHEN / THEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> OrderItemsRules.validateBeforeCreation(item)
        );

        assertEquals("La quantité doit être supérieure à 0.", exception.getMessage());
    }

    @Test
    @DisplayName("ERREUR - prix unitaire négatif")
    void should_throw_exception_when_unit_price_is_negative() {
        // GIVEN
        OrderItemsEntity item = new OrderItemsEntity();
        item.setQuantity(1);
        item.setUnitPrice(-5.0);

        // WHEN / THEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> OrderItemsRules.validateBeforeCreation(item)
        );

        assertEquals("Le prix unitaire ne peut pas être négatif.", exception.getMessage());
    }

    @Test
    @DisplayName("ERREUR - produit inactif")
    void should_throw_exception_when_product_is_inactive() {
        // GIVEN
        ProductEntity product = new ProductEntity();
        product.setIsActive(false);

        OrderItemsEntity item = new OrderItemsEntity();
        item.setQuantity(1);
        item.setUnitPrice(10.0);
        item.setProduct(product);

        // WHEN / THEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> OrderItemsRules.validateBeforeCreation(item)
        );

        assertEquals(
                "On ne peut pas ajouter au panier un produit inactif.",
                exception.getMessage()
        );
    }

}
