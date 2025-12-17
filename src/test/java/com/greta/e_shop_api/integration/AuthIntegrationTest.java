package com.greta.e_shop_api.integration;

import com.greta.e_shop_api.persistence.entities.UserEntity;
import com.greta.e_shop_api.persistence.repositories.UserRepository;
import com.greta.e_shop_api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturnJwtTokenWhenLoginIsValid() throws Exception {
        persistUser("admin@example.com", "admin123", UserEntity.Role.ADMIN);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@example.com",
                                  "password": "admin123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("admin@example.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void shouldRejectAccessWithInvalidToken() throws Exception {
        mockMvc.perform(post("/products")
                        .header("Authorization", "abcUtbjkInj.defJIOIFDhhgdgUKkkn.ghiLojjjGGytfH"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectAccessForUserRoleToAdminEndpoint() throws Exception {
        UserEntity user = persistUser("user@example.com", "user123", UserEntity.Role.USER);
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(post("/product")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Test",
                          "description": "Test",
                          "price": 10.0,
                          "stock": 1,
                          "isActive": true
                        }
                    """))
                .andExpect(status().isForbidden());
    }

    private UserEntity persistUser(String email, String rawPassword, UserEntity.Role role) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }
}
