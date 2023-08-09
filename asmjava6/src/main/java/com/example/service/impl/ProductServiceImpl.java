package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.jparepository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	
	@Override
	public Product findById(Integer id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).get();
	}

	

}
