package com.example.service.impl;

import org.springframework.stereotype.Service;

import com.example.entity.Order;
import com.example.service.OderService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class OrderServiceImpl implements OderService{

	@Override
	public Order create(JsonNode orderData) {
		
		return null;
	}

}
