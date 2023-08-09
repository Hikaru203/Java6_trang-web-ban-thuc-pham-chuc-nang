package com.example.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.OrderDTO;
import com.example.jparepository.OrderDetailRepository;
import com.example.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
     @Autowired
    private OrderDetailRepository orderDetailRepository; // Assume you have this repository

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
