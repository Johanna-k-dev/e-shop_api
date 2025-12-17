package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.domain.services.AddressService;
import com.greta.e_shop_api.domain.services.CustomerService;
import com.greta.e_shop_api.exposition.dtos.AddressRequestDTO;
import com.greta.e_shop_api.exposition.dtos.AddressResponseDTO;
import com.greta.e_shop_api.exposition.dtos.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final CustomerService customerService;


    @PostMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDTO> addAddressToCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody AddressRequestDTO dto
    ) {
        return ResponseEntity.ok(customerService.addAddress(customerId, dto));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequestDTO dto
    ) {
        return ResponseEntity.ok(addressService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AddressResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(addressService.searchByCity(keyword));
    }
}
