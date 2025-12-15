package com.greta.e_shop_api.config;

import com.greta.e_shop_api.domain.services.CustomUserDetailsService;
import com.greta.e_shop_api.security.JwtExceptionHandlerFilter;
import com.greta.e_shop_api.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //PUBLIC
                        .requestMatchers(HttpMethod.GET,
                                "/product/**",
                                "/category/**",
                                "/product-categories/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()

                        // ADMIN ONLY
                        .requestMatchers(HttpMethod.POST,   "/product/**", "/category/**", "/product-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/product/**", "/category/**", "/product-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/product/**", "/category/**", "/product-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**", "/category/**", "/product-categories/**").hasRole("ADMIN")

                        // USER + ADMIN
                        .requestMatchers(
                                "/addresses/**",
                                "/order/**",
                                "/order-items/**",
                                "/favorites/**",
                                "/customers/**"
                        ).hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                );


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtExceptionHandlerFilter, JwtFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
