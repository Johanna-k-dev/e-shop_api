package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.domain.rules.ProductCategoryRules;
import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.ProductCategoryRequestDTO;
import com.greta.e_shop_api.exposition.dtos.ProductCategoryResponseDTO;
import com.greta.e_shop_api.exposition.dtos.ProductResponseDTO;
import com.greta.e_shop_api.mappers.ProductCategoryMapper;
import com.greta.e_shop_api.mappers.ProductMapper;
import com.greta.e_shop_api.presistence.entities.CategoryEntity;
import com.greta.e_shop_api.presistence.entities.ProductCategoryEntity;
import com.greta.e_shop_api.presistence.entities.ProductEntity;
import com.greta.e_shop_api.presistence.repositories.CategoryRepository;
import com.greta.e_shop_api.presistence.repositories.ProductCategoryRepository;
import com.greta.e_shop_api.presistence.repositories.ProductRepository;
import io.micrometer.observation.ObservationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductCategoryResponseDTO create(ProductCategoryRequestDTO dto) {

        ProductEntity product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable : " + dto.productId()));

        CategoryEntity category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + dto.categoryId()));

        boolean alreadyExists = productCategoryRepository
                .findByProduct_IdAndCategory_Id(dto.productId(), dto.categoryId())
                .isPresent();

        ProductCategoryRules.validateBeforeCreation(alreadyExists);

        ProductCategoryEntity entity = new ProductCategoryEntity();
        entity.setProduct(product);
        entity.setCategory(category);

        return ProductCategoryMapper.toDto(productCategoryRepository.save(entity));
    }

    

    public List<ProductCategoryResponseDTO> getAll() {
        return productCategoryRepository.findAll()
                .stream()
                .map(ProductCategoryMapper::toDto)
                .toList();
    }

    public ProductCategoryResponseDTO getById(Long id) {
        ProductCategoryEntity entity = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association produit/catégorie introuvable : " + id));

        return ProductCategoryMapper.toDto(entity);
    }

    public void delete(Long id) {
        ProductCategoryEntity entity = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Association produit/catégorie introuvable : " + id));

        productCategoryRepository.delete(entity);
    }

    public List<ProductCategoryResponseDTO> findByProduct(Long productId) {
        return productCategoryRepository.findByProduct_Id(productId)
                .stream()
                .map(ProductCategoryMapper::toDto)
                .toList();
    }

    public List<ProductCategoryResponseDTO> findByCategory(Long categoryId) {
        return productCategoryRepository.findByCategory_Id(categoryId)
                .stream()
                .map(ProductCategoryMapper::toDto)
                .toList();
    }

    public Optional<ProductCategoryResponseDTO> findByProductAndCategory(Long productId, Long categoryId) {
        return productCategoryRepository
                .findByProduct_IdAndCategory_Id(productId, categoryId)
                .map(ProductCategoryMapper::toDto);
    }


    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + categoryId));

        return productCategoryRepository.findByCategory_Id(categoryId)
                .stream()
                .map(pc -> ProductMapper.toDto(pc.getProduct()))
                .toList();
    }

    public void deleteByProductAndCategory(Long productId, Long categoryId) {

        ProductCategoryEntity entity = productCategoryRepository
                .findByProduct_IdAndCategory_Id(productId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Association produit/catégorie introuvable (productId=" + productId + ", categoryId=" + categoryId + ")"
                ));

        productCategoryRepository.delete(entity);
    }

}
