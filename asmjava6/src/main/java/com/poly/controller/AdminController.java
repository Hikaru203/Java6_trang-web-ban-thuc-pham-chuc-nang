package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/index")
    public String index() {
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
    public String AddUsers() {
        return "admin/AddUser";
    }

    @GetMapping("/admin/ManagedAccount")
    public String ManagedAccount() {
        return "admin/ManagedAccount";
    }

    @GetMapping("/admin/ManagedBrands")
    public String ManagedBrands() {
        return "admin/ManagedBrands";
    }

    @GetMapping("/admin/ManagedCategories")
    public String ManagedCategories() {
        return "admin/ManagedCategories";
    }

    @GetMapping("/admin/ManagedDiscount")
    public String ManagedDiscount() {
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
