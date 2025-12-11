package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.FavoriteResponseDTO;
import com.greta.e_shop_api.mappers.FavoriteMapper;
import com.greta.e_shop_api.presistence.entities.CustomerEntity;
import com.greta.e_shop_api.presistence.entities.FavoriteEntity;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.CustomerRepository;
import com.greta.e_shop_api.presistence.repositories.FavoriteRepository;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public List<FavoriteResponseDTO> getAll() {
        return favoriteRepository.findAll()
                .stream()
                .map(FavoriteMapper::toDto)
                .toList();
    }

    public List<FavoriteEntity> getFavoritesByCustomer(Long customerId) {
        return favoriteRepository.findByCustomer_Id(customerId);
    }

    public FavoriteEntity addFavorite(Long customerId, Long productId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable avec cet identifiant " + customerId));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID " + productId));

        return favoriteRepository.findByCustomer_IdAndProduct_Id(customerId, productId)
                .orElseGet(() -> {
                    FavoriteEntity favorite = new FavoriteEntity();
                    favorite.setCustomer(customer);
                    favorite.setProduct(product);
                    return favoriteRepository.save(favorite);
                });
    }

    public void removeFavorite(Long customerId, Long productId) {
        FavoriteEntity favorite = favoriteRepository
                .findByCustomer_IdAndProduct_Id(customerId, productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Favorite not found for customer " + customerId + " and product " + productId
                ));

        favoriteRepository.delete(favorite);
    }
}

