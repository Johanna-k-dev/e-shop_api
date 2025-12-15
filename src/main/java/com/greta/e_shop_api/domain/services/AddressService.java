package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.AddressRequestDTO;
import com.greta.e_shop_api.exposition.dtos.AddressResponseDTO;
import com.greta.e_shop_api.mappers.AddressMapper;
import com.greta.e_shop_api.persistence.entities.AddressEntity;
import com.greta.e_shop_api.persistence.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressResponseDTO create(AddressRequestDTO dto) {
        AddressEntity entity = AddressMapper.toEntity(dto);
        return AddressMapper.toDto(addressRepository.save(entity));
    }

    public List<AddressResponseDTO> getAll() {
        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    public AddressResponseDTO getById(Long id) {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + id));

        return AddressMapper.toDto(entity);
    }

    public AddressResponseDTO update(Long id, AddressRequestDTO dto) {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + id));

        entity.setStreet(dto.street());
        entity.setCity(dto.city());
        entity.setZipCode(dto.zipCode());
        entity.setCounty(dto.county());

        return AddressMapper.toDto(addressRepository.save(entity));
    }

    public void delete(Long id) {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + id));

        addressRepository.delete(entity);
    }

    public List<AddressResponseDTO> searchByCity(String keyword) {
        return addressRepository.findByCityContainingIgnoreCase(keyword)
                .stream()
                .map(AddressMapper::toDto)
                .toList();
    }
}
