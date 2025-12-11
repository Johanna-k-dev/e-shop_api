package com.greta.e_shop_api.domain.services;

import com.greta.e_shop_api.exceptions.ResourceNotFoundException;
import com.greta.e_shop_api.exposition.dtos.CustomerRequestDTO;
import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import com.greta.e_shop_api.mappers.CustomerMapper;
import com.greta.e_shop_api.presistence.entities.AddressEntity;
import com.greta.e_shop_api.presistence.entities.CustomerEntity;

import com.greta.e_shop_api.presistence.repositories.AddressRepository;
import com.greta.e_shop_api.presistence.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {

        CustomerEntity entity = CustomerMapper.toEntity(dto);


        if (dto.addressId() != null) {
            AddressEntity address = addressRepository.findById(dto.addressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + dto.addressId()));
            entity.setAddress(address);
        } else {
            entity.setAddress(null);
        }

        CustomerEntity saved = customerRepository.save(entity);
        return CustomerMapper.toDto(saved);
    }


    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public CustomerResponseDTO getById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + id));

        return CustomerMapper.toDto(entity);
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {

        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + id));

        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());

        if (dto.addressId() != null) {
            AddressEntity address = addressRepository.findById(dto.addressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adresse introuvable : " + dto.addressId()));
            entity.setAddress(address);
        }

        CustomerEntity updated = customerRepository.save(entity);
        return CustomerMapper.toDto(updated);
    }


    public void delete(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + id));

        customerRepository.delete(entity);
    }

    public List<CustomerResponseDTO> searchByLastName(String keyword) {
        return customerRepository.findByLastNameContainingIgnoreCase(keyword)
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }
}
