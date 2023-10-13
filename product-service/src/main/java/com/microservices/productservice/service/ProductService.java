package com.microservices.productservice.service;


import com.microservices.productservice.dto.ProductCreateDto;
import com.microservices.productservice.dto.ProductGetDto;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;

    public void createProduct(ProductCreateDto productCreateDto) {
        Product product = Product.builder()
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .price(productCreateDto.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductGetDto> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductGetDto).toList();
    }

    private ProductGetDto mapToProductGetDto(Product product) {
        return ProductGetDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

    }
}
