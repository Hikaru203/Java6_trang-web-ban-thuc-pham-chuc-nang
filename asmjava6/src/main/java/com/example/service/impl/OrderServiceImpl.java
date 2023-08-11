package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Order;
import com.example.service.OderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.jparepository.OrderRepository;
@Service
public class OrderServiceImpl implements OderService{
	@Autowired
	OrderRepository OrderRepository;
	
	@Override
	public Order create(JsonNode orderData) {
		
		return null;
	}

	@Override
	public void save(Order order) {
		OrderRepository.save(order);
		
	}

}
