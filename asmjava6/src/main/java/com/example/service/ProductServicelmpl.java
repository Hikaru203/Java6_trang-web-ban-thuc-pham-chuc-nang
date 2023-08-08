package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.jparepository.ProductRepository;

@Service
public class ProductServicelmpl implements ProductService  {
  @Autowired
  ProductRepository daoProduct;

@Override
public List<Product> findAll() {
	
	return daoProduct.findAll();
}
}
