package com.example.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Order;
import com.example.entity.OrderDTO;
import com.example.jparepository.OrderDetailRepository;
import com.example.jparepository.OrderRepository;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository; // Assume you have this repository
    
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
    public List<OrderDTO> getComplexOrders() {
        List<Object[]> result = orderDetailRepository.getComplexOrders();
        System.out.println("Number of rows returned: " + result.size());
        List<OrderDTO> complexOrders = new ArrayList<>();
        for (Object[] row : result) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId((int) row[0]);
            orderDTO.setAdress((String) row[1]);
            orderDTO.setFullname((String) row[2]);
            orderDTO.setPhoneNumber((String) row[3]);
            orderDTO.setOrderDate((Timestamp) row[4]);
            orderDTO.setTotalPrice((BigDecimal) row[5]);
            orderDTO.setProductNames((String) row[6]);
            complexOrders.add(orderDTO);
        }
        return complexOrders;
    }
}
