package com.example.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AddressDistrict")
public class AddressDistrict implements Serializable {
	@Id
	@Column(name = "addressdistrict_id")
	private int addressdistrict_id;

	@Column(name = "name")
	private String name;

	// Getters and Setters
}
