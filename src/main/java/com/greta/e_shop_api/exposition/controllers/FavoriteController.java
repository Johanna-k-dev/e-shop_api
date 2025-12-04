package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.FavoriteService; // 👈 important
import com.greta.e_shop_api.presistence.entities.FavoriteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<FavoriteEntity>> getAllFavoritesByCustomerId(@PathVariable Long customerId) {
        List<FavoriteEntity> favorites = favoriteService.getFavoritesByCustomer(customerId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<FavoriteEntity> addFavorite(@PathVariable Long customerId,
                                                      @PathVariable Long productId) {
        FavoriteEntity favorite = favoriteService.addFavorite(customerId, productId);
        return ResponseEntity.ok(favorite);
    }

    @DeleteMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long customerId,
                                               @PathVariable Long productId) {
        favoriteService.removeFavorite(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
