package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Product;
import com.example.jparepository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class HomeController {
	@Autowired
	ProductRepository daoProduct;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		List<Product> page = daoProduct.findAll();
		model.addAttribute("products", page);
		return "index";
	}

}
