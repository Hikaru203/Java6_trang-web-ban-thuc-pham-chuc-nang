package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jparepository.CategoryRepository;
import com.example.jparepository.DiscountRepository;
import com.example.jparepository.ProductRepository;
import com.example.jparepository.AccountRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
    @GetMapping({ "/admin/index" })
    public String GetProducts() {
        return "admin/index";
    }

    @GetMapping({ "/admin/AddProduct" })
    public String AddProducts() {
        return "admin/AddProduct";
    }

    @GetMapping({ "/admin/EditProduct/{id}" })
    public String EditProducts() {
        return "admin/AddProduct";
    }

}
