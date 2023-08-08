package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Order;
import com.example.entity.Product;
import com.example.jparepository.ProductRepository;
import com.example.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.service.OderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
	@Autowired
	OderService OderService;
	
	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return OderService.create(orderData);
		
	}
	
	
}
