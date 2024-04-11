package com.capstoneproject.brandservice.controller;

import com.capstoneproject.brandservice.dto.BrandRequest;
import com.capstoneproject.brandservice.dto.BrandResponse;
import com.capstoneproject.brandservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBrand(@RequestBody BrandRequest brandRequest) {
        brandService.createBrand(brandRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {
        brandService.updateBrand(id,brandRequest);
    }

    // Get an item by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BrandResponse getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }
}
