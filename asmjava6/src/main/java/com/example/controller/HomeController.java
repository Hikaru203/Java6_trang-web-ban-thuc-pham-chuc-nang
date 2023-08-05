package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Product;
import com.example.jparepository.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	ProductRepository daoProduct;

	@RequestMapping("/index")
	public String index(Model model,@RequestParam("cid") Optional<Integer> cid) {
		
		if(cid.isPresent()) {
			
			List<Product> page = daoProduct.findByCategory(cid.get());
			model.addAttribute("products", page);
		}else {
			List<Product> page = daoProduct.findAll();
			model.addAttribute("products", page);
		}
	
		return "index";
	}

	@RequestMapping(value = "/detail/{id}")
	public String detail(Model model,@PathVariable("id")int id) {
		Product item= daoProduct.findById(id).get();
		model.addAttribute("itemDetail",item);
		return "detail";
	}
	@RequestMapping(value = "/cart")
	public String cart() {
		
		return "cart";
	}
}
