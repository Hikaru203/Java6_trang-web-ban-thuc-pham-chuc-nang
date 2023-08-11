package com.example.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Account user;
    
    @JoinColumn(name = "adress", nullable = false)
    private String adress;
    // Getters and setters, constructors, and other methods
    @JoinColumn(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @JoinColumn(name = "country", nullable = false)
    private String country;
    
    @Column(name = "date_order")
    private Date orderDate;
    
    @Column(name = "total_price")
    private int totalPrice;
    
    @JoinColumn(name = "is_acctive")
    private boolean isActive;

	
}


