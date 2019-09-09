package com.shevtsov.controller;

import com.shevtsov.domain.Product;
import com.shevtsov.exception.ProductNotFoundException;
import com.shevtsov.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/{id}")
    @ExceptionHandler(ProductNotFoundException.class)
    public Product getProduct(@PathVariable(value = "id") Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @GetMapping("/products")
    public Iterable<Product> getProduct() {
        return productRepository.findAll();
    }
}
