package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Discount;
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

    @GetMapping({ "/ManagedAccount" })
    public List<Account> getAccounts() {
        return userRepository.findAll();
    }

    @GetMapping({ "/ManagedAccount/{id}" })
    public Account getAccount(@PathVariable("id") int id) {
        return userRepository.findById(id);
    }

    @PostMapping({ "/ManagedAccount" })
    public Account addAccounts(@RequestBody Account account) {
        account.setActive(true);
        this.userRepository.save(account);
        return account;
    }

    @PutMapping({ "/ManagedAccount/{id}" })
    public Account updateAccount(@PathVariable("id") Integer id, @RequestBody Account account) {
        this.userRepository.save(account);
        return account;
    }

    @GetMapping({ "/ManagedDiscount" })
    public Object getDiscounts() {
        return discountRepository.findAll();
    }

    @GetMapping({ "/ManagedDiscount/{id}" })
    public Optional<Discount> getDiscount(@PathVariable("id") int id) {
        return discountRepository.findById(id);
    }

    @PostMapping({ "/ManagedDiscount" })
    public Discount addDiscount(@RequestBody Discount discount) {
        this.discountRepository.save(discount);
        return discount;
    }

    @PutMapping({ "/ManagedDiscount/{id}" })
    public Discount updateDiscount(@PathVariable("id") Integer id, @RequestBody Discount discount) {
        this.discountRepository.save(discount);
        return discount;
    }

    @DeleteMapping({ "/ManagedDiscount/{id}" })
    public void deleteDiscount(@PathVariable("id") Integer id) {
        this.discountRepository.deleteById(id);
    }

}
