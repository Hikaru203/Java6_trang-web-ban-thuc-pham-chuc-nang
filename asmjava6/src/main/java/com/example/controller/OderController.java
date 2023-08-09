package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OderController {

	@RequestMapping("/client/checkout")
	public String checkOut() {
		return "checkout";
	}
	@RequestMapping("/cartDetail")
	public String checkOutCart() {
		return "cartDetail";
	}
}
