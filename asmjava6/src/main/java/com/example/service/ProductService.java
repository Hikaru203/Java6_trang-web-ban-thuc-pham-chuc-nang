package com.example.service;

import org.springframework.data.domain.Page;

import com.example.entity.Product;

public interface ProductService {

	
	Product findById(Integer id);

    Page<Product> getPaginatedProducts(int page, int pageSize);

	

}
