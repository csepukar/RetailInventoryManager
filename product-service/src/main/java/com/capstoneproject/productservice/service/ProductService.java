package com.capstoneproject.productservice.service;

import com.capstoneproject.productservice.dto.ProductRequest;
import com.capstoneproject.productservice.dto.ProductResponse;
import com.capstoneproject.productservice.model.Product;
import com.capstoneproject.productservice.repository.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .title(productRequest.getTitle())
                .summary(productRequest.getSummary())
                .type(productRequest.getType())
                .content(productRequest.getContent())
                .build();
        product.setCreatedBy("ram"); //need to implement login and name should come from userid
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id).map(this::mapToProductResponse).get();
    }

    public void updateProduct(Long id, ProductRequest productRequest){
        Optional<Product> productOpt = productRepository.findById(id);
        if(productOpt.isPresent()){
            Product product = productOpt.map(popt ->
                            Product.builder()
                                .id(popt.getId())
                                .title(productRequest.getTitle())
                                .summary(productRequest.getSummary())
                                .type(productRequest.getType())
                                .content(productRequest.getContent())
                                .createdBy(popt.getCreatedBy())
                                .updatedBy("shyam") //change the name with the login credential
                                .createdAt(popt.getCreatedAt())
                                .updatedAt(LocalDateTime.now())
                                    .build()
                            ).get();
            productRepository.saveAndFlush(product);
            log.info("Product {} is updated", product.getId());
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .summary(product.getSummary())
                .type(product.getType())
                .content(product.getContent())
                .build();
    }
}
