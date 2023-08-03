package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.jparepository.CategoryRepository;
import com.example.jparepository.DiscountRepository;
import com.example.jparepository.ProductRepository;
import com.example.jparepository.UserRepository;

@CrossOrigin({ "*" })
@RestController
public class AdminAPI {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({ "/ManagedProduct" })
    public Object getProducts() {
        return this.productRepository.findAll();
    }

    @PostMapping({ "/ManagedProduct" })
    public Product addProduct(@RequestBody Product product) {
        this.productRepository.save(product);
        return product;
    }

}
