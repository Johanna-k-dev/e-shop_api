package com.greta.e_shop_api.exposition.controllers;


import com.greta.e_shop_api.presistence.entities.FavoriteEntity;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.CustomerRepository;
import com.greta.e_shop_api.presistence.repositories.FavoriteRepository;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    List<FavoriteEntity> getAllFavorites(){
        List<FavoriteEntity> favorites = favoriteRepository.findAll() ;
        return favorites;
    };

    // @GetMapping("/customer-id") List<FavoriteEntity> getAllFavoritesByCustomerId(@RequestParam Long customerId){}
}
