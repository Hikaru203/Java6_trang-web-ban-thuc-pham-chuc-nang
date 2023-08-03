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

    
}
