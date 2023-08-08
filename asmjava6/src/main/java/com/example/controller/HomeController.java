package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Account;
import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.jparepository.ProductRepository;
import com.example.service.CartService;

@Controller
public class HomeController {
	@Autowired
	ProductRepository daoProduct;

	@Autowired
	CartService cartService;

	@RequestMapping(value = "/client/detail/{id}")
	public String detail(Model model, @PathVariable("id") int id) {
		Product item = daoProduct.findById(id).get();
		model.addAttribute("itemDetail", item);
		return "detail";
	}

	@RequestMapping(value = "/client/login/success")
	public String ht(Model model) {
		return "redirect:/client/index";
	}

	@RequestMapping(value = "/client/social/success")
	public String loginGG(Model model) {

		return "redirect:/client/index";
	}

	@RequestMapping("/client/login")
	public String showLoginFrom(Model model) {
		model.addAttribute("user", new Account());
		return "login";
	}

	@RequestMapping("/client/denied")
	public String error(Model model) {
		System.out.println("chú m k có tuổi");
		return "redirect:/client/index";
	}

	@RequestMapping("/client/signin")
	public String showsinupFrom(Model model) {
		model.addAttribute("user", new Account());
		return "dangky";
	}

	@RequestMapping("/client/index")
	public String index(Model model, @RequestParam("cid") Optional<Integer> cid) {
		System.out.println(cid);
		if (cid.isPresent()) {

			List<Product> page = daoProduct.findByCategory(cid.get());
			model.addAttribute("products", page);
		} else {
			List<Product> page = daoProduct.findAll();
			model.addAttribute("products", page);
		}
		return "index";
	}

	@RequestMapping("/client/cart")
	public String cart(Model model) {
		List<Cart> list = cartService.findAll();
		model.addAttribute("carts", list);
		return "cart";
	}

}
