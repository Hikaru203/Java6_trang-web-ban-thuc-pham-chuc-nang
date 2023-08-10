package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.VNPayService;
import com.example.entity.AddressDistrict;
import com.example.entity.Order;
import com.example.entity.Product;
import com.example.jparepository.AddressDistrictJpaRepository;
import com.example.jparepository.ProductRepository;
import com.example.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;

import com.example.service.AddressDistrictService;
import com.example.service.OderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
	 private final AddressDistrictJpaRepository addressDistrictRepository;

	    @Autowired
	    public OrderRestController(AddressDistrictJpaRepository addressDistrictRepository) {
	        this.addressDistrictRepository = addressDistrictRepository;
	    }

	    @GetMapping
	    public List<AddressDistrict> getAllDistricts() {
	        List<AddressDistrict> districts = addressDistrictRepository.findAll();
	        for (AddressDistrict district : districts) {
	            System.out.println("District name: " + district.getName());
	        }
	        return districts;
	    }

}
