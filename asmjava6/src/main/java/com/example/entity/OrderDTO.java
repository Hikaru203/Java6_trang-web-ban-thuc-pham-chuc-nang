package com.example.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDTO {
    private int id;
    private String adress;
    private String fullname;
    private String phoneNumber;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private String productNames;

    // Getters and setters
}

