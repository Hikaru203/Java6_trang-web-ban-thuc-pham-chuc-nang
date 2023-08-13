package com.example.service;

import com.example.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;

public interface OderService {

	Order create(JsonNode orderData);

	void save(Order order);

	void setActiveForOrder(int orderId);

	Order findById(Integer orderId);

	

}
