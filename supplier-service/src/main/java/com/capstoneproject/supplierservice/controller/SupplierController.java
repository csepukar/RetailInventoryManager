package com.capstoneproject.supplierservice.controller;

import com.capstoneproject.supplierservice.dto.SupplierRequest;
import com.capstoneproject.supplierservice.dto.SupplierResponse;
import com.capstoneproject.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSupplier(@RequestBody SupplierRequest supplierRequest) {
        supplierService.createSupplier(supplierRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@PathVariable Long id, @RequestBody SupplierRequest supplierRequest) {
        supplierService.updateSupplier(id,supplierRequest);
    }

    // Get an item by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SupplierResponse getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }
}
