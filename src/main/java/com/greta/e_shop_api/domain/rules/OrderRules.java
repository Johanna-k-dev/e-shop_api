package com.greta.e_shop_api.domain.rules;

import com.greta.e_shop_api.presistence.entities.OrderEntity;

public class OrderRules {

    public static void validateBeforeCreation(OrderEntity order) {
        if (order.getCustomer() == null) {
            throw new RuntimeException("Une commande doit être liée à un client.");
        }
        if (order.getAddress() == null) {
            throw new RuntimeException("Une commande doit contenir une adresse.");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new RuntimeException("Une commande doit contenir au moins un item.");
        }
    }

    public static void validateBeforeStatusChange(OrderEntity order, String newStatus) {
        String current = order.getStatus();

        if ("PAID".equals(current) && "CREATED".equals(newStatus)) {
            throw new RuntimeException("On ne peut pas repasser une commande payée en statut CREATED.");
        }

        if ("CANCELLED".equals(current)) {
            throw new RuntimeException("On ne peut pas modifier une commande déjà annulée.");
        }
    }

    public static void validateBeforeDeletion(OrderEntity order) {
        if ("PAID".equals(order.getStatus())) {
            throw new RuntimeException("On ne peut pas supprimer une commande payée.");
        }
    }
}
