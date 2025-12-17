package com.greta.e_shop_api.exposition.dtos;

public record AdminCreateRequestDTO(
        String email,
        String password
) {}