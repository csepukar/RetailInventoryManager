package com.capstoneproject.brandservice.service;

import com.capstoneproject.brandservice.dto.BrandRequest;
import com.capstoneproject.brandservice.dto.BrandResponse;
import com.capstoneproject.brandservice.model.Brand;
import com.capstoneproject.brandservice.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    public void createBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.getName())
                .description(brandRequest.getDescription())
                .logo(brandRequest.getLogo())
                .website(brandRequest.getWebsite())
                .address(brandRequest.getAddress())
                .email(brandRequest.getEmail())
                .phoneNumber(brandRequest.getPhoneNumber())
                .activeStatus(brandRequest.getActiveStatus())
                .build();
        brand.setCreatedBy("ram"); //need to implement login and name should come from userid
        brand.setCreatedAt(LocalDateTime.now());
        brandRepository.save(brand);
        log.info("Brand {} is saved", brand.getId());
    }

    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream().map(this::mapToBrandResponse).toList();
    }

    public BrandResponse getBrandById(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return optionalBrand.isPresent() ? optionalBrand.map(this::mapToBrandResponse).get() : null;
    }

    public void updateBrand(Long id, BrandRequest brandRequest) {
        Optional<Brand> brandOpt = brandRepository.findById(id);
        if (brandOpt.isPresent()) {
            Brand brand = brandOpt.map(opt ->
                    Brand.builder()
                            .id(opt.getId())
                            .name(brandRequest.getName() != null ? brandRequest.getName() : opt.getName())
                            .description(brandRequest.getDescription() != null ? brandRequest.getDescription() : opt.getDescription())
                            .logo(brandRequest.getLogo() != null ? brandRequest.getLogo() : opt.getLogo())
                            .website(brandRequest.getWebsite() != null ? brandRequest.getWebsite() : opt.getLogo())
                            .address(brandRequest.getAddress() != null ? brandRequest.getAddress() :opt.getAddress())
                            .email(brandRequest.getEmail() != null ? brandRequest.getEmail() : opt.getEmail())
                            .phoneNumber(brandRequest.getPhoneNumber() != null ? brandRequest.getPhoneNumber() : opt.getPhoneNumber())
                            .activeStatus(brandRequest.getActiveStatus() != null ? brandRequest.getActiveStatus() : opt.getActiveStatus())
                            .createdBy(opt.getCreatedBy())
                            .updatedBy("shyam") //change the name with the login credential
                            .createdAt(opt.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            ).get();
            brandRepository.saveAndFlush(brand);
            log.info("Brand {} is updated", brand.getId());
        }
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .logo(brand.getLogo())
                .website(brand.getWebsite())
                .address(brand.getAddress())
                .email(brand.getEmail())
                .phoneNumber(brand.getPhoneNumber())
                .activeStatus(brand.getActiveStatus())
                .build();
    }
}
