package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}