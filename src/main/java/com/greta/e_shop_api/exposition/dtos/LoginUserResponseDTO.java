package com.greta.e_shop_api.exposition.dtos;

import com.greta.e_shop_api.persistence.entities.UserEntity;

public record LoginUserResponseDTO(
        String token,
        String email,
        String role
) {

    public static LoginUserResponseDTO fromEntity(String token, UserEntity user) {
        return new LoginUserResponseDTO(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
