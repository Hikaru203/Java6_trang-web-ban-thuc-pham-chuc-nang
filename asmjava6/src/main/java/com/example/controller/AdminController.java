package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

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

    @GetMapping({ "/admin/ManagedBrands" })
    public String ManagedBrands() {
        return "admin/ManagedBrands";
    }

    @GetMapping({ "/admin/ManagedDistributors" })
    public String ManagedDistributors() {
        return "admin/ManagedDistributors";
    }

    @GetMapping({ "/admin/ManagedOrders" })
    public String ManagedOrders() {
        return "admin/ManagedOrders";
    }
    @GetMapping({ "/admin/ReportFavoriteProducts" })
    public String ReportFavoriteProducts() {
        return "admin/ReportFavoriteProducts";
    }
    
    @GetMapping({ "/admin/ReportRevenue" })
    public String ReportRevenue() {
        return "admin/ReportRevenue";
    }
    
    @GetMapping({ "/admin/ReportProduct" })
    public String ReportProduct() {
        return "admin/ReportProduct";
    }
}
