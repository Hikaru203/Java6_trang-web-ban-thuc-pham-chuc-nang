package com.example.service.impl;

import java.util.Optional;

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
	@Override
    public void setActiveForOrder(int orderId) {
        Optional<Order> optionalOrder = OrderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setActive(true);
            OrderRepository.save(order);
        } else {
            // Xử lý trường hợp không tìm thấy đơn hàng với ID cụ thể
            // (ví dụ: thông báo lỗi, redirect, ...)
        }
    }

	@Override
	public Order findById(Integer orderId) {
		// TODO Auto-generated method stub
		return OrderRepository.findById(orderId).get();
	}
	

}
