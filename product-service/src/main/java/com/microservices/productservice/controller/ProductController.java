package com.microservices.productservice.controller;


import com.microservices.productservice.dto.ProductCreateDto;
import com.microservices.productservice.dto.ProductGetDto;
import com.microservices.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductCreateDto productCreateDto){
        productService.createProduct(productCreateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductGetDto> getAllProducts(){
        return productService.getAllProducts();
    }


}
