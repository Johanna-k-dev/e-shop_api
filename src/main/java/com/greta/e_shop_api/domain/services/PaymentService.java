package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.exposition.dtos.PaymentRequestDTO;
import com.greta.e_shop_api.exposition.dtos.PaymentResponseDTO;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.persistence.entities.OrderEntity;
import com.greta.e_shop_api.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponseDTO checkout(PaymentRequestDTO dto, Authentication auth) {

        OrderEntity order = orderRepository.findById(dto.orderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Commande introuvable : " + dto.orderId())
                );


        String email = auth.getName();

        if (order.getCustomer() == null
                || order.getCustomer().getUser() == null
                || !email.equals(order.getCustomer().getUser().getEmail())) {
            throw new AccessDeniedException("Paiement interdit pour cette commande.");
        }

        // 💳 MOCK PAYMENT
        order.setStatus("PAID");
        orderRepository.save(order);

        return new PaymentResponseDTO("PAID");
    }
}
