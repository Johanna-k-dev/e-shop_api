package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.CategoryRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CategoryResponseDTO;
import com.greta.e_shop_api.mappers.CategoryMapper;
import com.greta.e_shop_api.presistence.entities.CategoryEntity;
import com.greta.e_shop_api.presistence.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        CategoryEntity entity = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDto(categoryRepository.save(entity));
    }

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDTO getById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + id));
        return CategoryMapper.toDto(entity);
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + id));

        entity.setLabel(dto.label());

        return CategoryMapper.toDto(categoryRepository.save(entity));
    }

    public void delete(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + id));

        categoryRepository.delete(entity);
    }

    public List<CategoryResponseDTO> search(String keyword) {
        return categoryRepository.findByLabelContainingIgnoreCase(keyword)
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }
}
