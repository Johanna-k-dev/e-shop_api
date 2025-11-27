package com.greta.e_shop_api.exposition.controllers;

import com.greta.e_shop_api.presistence.entities.CustomerEntity;
import com.greta.e_shop_api.presistence.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    private List<CustomerEntity> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        return customers;
    }

    @GetMapping("/search")
    private List<CustomerEntity> searchCustomers(@RequestParam String keyword) {
        List<CustomerEntity> customers = customerRepository.findByLastNameContainingIgnoreCase(keyword);
        return customers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> saveCustomer(@RequestBody CustomerEntity customer) {
        CustomerEntity saved = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity newCustomer) {

        CustomerEntity existing = customerRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setFirstName(newCustomer.getFirstName());
            existing.setLastName(newCustomer.getLastName());
            CustomerEntity updated = customerRepository.save(existing);
            return ResponseEntity.ok(updated);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerEntity> deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
