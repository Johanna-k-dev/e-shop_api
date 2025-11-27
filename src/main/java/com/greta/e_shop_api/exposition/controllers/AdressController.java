package com.greta.e_shop_api.exposition.controllers;


import com.greta.e_shop_api.presistence.entities.AddressEntity;
import com.greta.e_shop_api.presistence.entities.CategoryEntity;
import com.greta.e_shop_api.presistence.repositories.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AdressController {

    @Autowired
    private AdressRepository adressRepository;

    @GetMapping
    public List<AddressEntity> getAllAdress () {
        List<AddressEntity> addresses = adressRepository.findAll();
        return addresses;
    }

}
