package com.greta.e_shop_api.persistence.entities;


import com.greta.e_shop_api.exposition.dtos.ProductRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double discount = 0.0;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDate promoStart;
    private LocalDate promoEnd;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void updateFrom(ProductRequestDTO dto) {
        this.name = dto.name();
        this.price = dto.price();
        this.stock = dto.stock();
        this.discount = dto.discount();
        this.imageUrl = dto.imageUrl();
        this.isActive = dto.isActive();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "product")
    private List<ProductCategoryEntity> productCategories = new ArrayList<>();

}
