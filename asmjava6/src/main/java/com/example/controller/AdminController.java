package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jparepository.CategoryRepository;
import com.example.jparepository.DiscountRepository;
import com.example.jparepository.ProductRepository;
import com.example.jparepository.AccountRepository;

@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin/index")
    public String index(Model model) {

        model.addAttribute("products", productRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/AddBrands")
    public String AddBrands() {
        return "admin/AddBrands";
    }

    @GetMapping("/admin/AddCategories")
    public String AddCategories() {
        return "admin/AddCategories";
    }

    @GetMapping("/admin/AddDiscount")
    public String AddDiscount() {
        return "admin/AddDiscount";
    }

    @GetMapping("/admin/AddDistributors")
    public String AddDistributors() {
        return "admin/AddDistributors";
    }

    @GetMapping("/admin/AddProduct")
    public String AddProduct() {
        return "admin/AddProduct";
    }

    @GetMapping("/admin/AddUser")
    public String AddUsers(Model model) {

        return "admin/AddUser";
    }

    @GetMapping("/admin/ManagedAccount")
    public String ManagedAccount(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/ManagedAccount";
    }

    @GetMapping("/admin/ManagedBrands")
    public String ManagedBrands() {
        return "admin/ManagedBrands";
    }

    @GetMapping("/admin/ManagedCategories")
    public String ManagedCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/ManagedCategories";
    }

    @GetMapping("/admin/ManagedDiscount")
    public String ManagedDiscount(Model model) {
        model.addAttribute("discounts", discountRepository.findAll());
        return "admin/ManagedDiscount";
    }

    @GetMapping("/admin/ManagedDistributors")
    public String ManagedDistributors() {
        return "admin/ManagedDistributors";
    }

    @GetMapping("/admin/ManagedOrders")
    public String ManagedOrders() {
        return "admin/ManagedOrders";
    }

    @GetMapping("/admin/ReportFavoriteProducts")
    public String ReportFavoriteProducts() {
        return "admin/ReportFavoriteProducts";
    }

    @GetMapping("/admin/ReportProduct")
    public String ReportProduct() {
        return "admin/ReportProduct";
    }

    @GetMapping("/admin/ReportRevenue")
    public String ReportRevenue() {
        return "admin/ReportRevenue";
    }
}
