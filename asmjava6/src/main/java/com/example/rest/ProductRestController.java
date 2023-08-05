package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.jparepository.ProductRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductRepository daoProduct;

	@GetMapping("{id}")
	public Product getOne(@PathVariable("id")Integer id) {
		return daoProduct.findById(id).get();
	}
}
