package com.greta.e_shop_api.exposition.dtos;

import com.greta.e_shop_api.persistence.entities.UserEntity;

public record RegisterUserRequestDTO (
        String email,
        String password
){
    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setRole(UserEntity.Role.USER);

        return user;
    }
}
