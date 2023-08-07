package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.CategoryRepository;
import com.example.jparepository.DiscountRepository;
import com.example.jparepository.ProductRepository;

@CrossOrigin({ "*" })
@RestController
public class AdminAPI {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({ "/ManagedProduct" })
    public Object getProducts() {
        return productRepository.findAll();

    }

    @GetMapping({ "/ManagedProduct/{id}" })
    public Optional<Product> getProduct(@PathVariable("id") int id) {
        return productRepository.findById(id);
    }

    @GetMapping({ "/ManagedCategories" })
    public Object getCategories() {
        return this.categoryRepository.findAll();
    }

    @PostMapping({ "/ManagedProduct" })
    public Product addProduct(@RequestBody Product product) {
        product.setActive(true);
        this.productRepository.save(product);
        return product;
    }

    @PutMapping({ "/ManagedProduct/{id}" })
    public Product updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        this.productRepository.save(product);
        return product;
    }

}
