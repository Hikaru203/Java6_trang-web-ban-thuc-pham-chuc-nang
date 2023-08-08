package com.example.controller;


import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.entity.Account;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.ProductRepository;
import com.example.service.CookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class HomeController {
	@Autowired
	ProductRepository daoProduct;
	@Autowired
	AccountRepository daoAccount;
	@Autowired
	private CookieService cookieService;
	@RequestMapping("/client/login")
	public String lgoin(Model model) {
		model.addAttribute("user", new Account());
		return "login";
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
//		List<Cart> cartItem=cartService.findAll();
//		model.addAttribute("cartItem",cartItem);
		return "index";
	}

	@RequestMapping(value = "/client/detail/{id}")
	public String detail(Model model, @PathVariable("id") int id) {
		Product item = daoProduct.findById(id).get();
		model.addAttribute("itemDetail", item);
		return "detail";
	}

	   @RequestMapping(value = "/client/login/success")
	    public String success(Model model, HttpServletResponse response) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	            String username = userDetails.getUsername();

	            Account account = daoAccount.findByUserName(username);

	            if (account != null) {
                String cleanedUsername = account.getUserName().replaceAll("\\s", "");
                cookieService.setCookie(response, "username", cleanedUsername, 3600);
                System.out.println("Đăng nhập thành công");
	            } else {
	                System.out.println("Không tìm thấy tài khoản");
	          }

           return "redirect:/client/index";
      } else {
          return "redirect:/client/index";
       }
	}
	  @RequestMapping(value = "/client/signin/error")
	   public String loi (Model model) {
		  model.addAttribute("loi", "Sai thông tin đăng nhập, Vui lòng nhập lại");
		  return "login";
	  }
	  
	  @RequestMapping(value = "/client/social/success")
	   public String loginGG (Model model) {
		 
		  return "redirect:/client/index";
	  }
}