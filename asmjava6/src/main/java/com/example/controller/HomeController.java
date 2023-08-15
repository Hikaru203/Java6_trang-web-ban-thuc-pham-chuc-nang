package com.example.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Account;
import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.ProductRepository;
import com.example.service.CartService;
import com.example.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	ProductRepository daoProduct;
	@Autowired
	CartService cartService;
	@Autowired
	AccountRepository daoAccount;
	@Autowired
	CookieService cookieService;

	@RequestMapping(value = "/client/detail/{id}")
	public String detail(Model model, @PathVariable("id") int id) {
		Product item = daoProduct.findById(id).get();
		model.addAttribute("itemDetail", item);
		return "detail";
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
		return "redirect:/client/index";
	}

	@RequestMapping("/client/signin")
	public String showsinupFrom(Model model) {
		model.addAttribute("user", new Account());
		return "dangky";
	}

	@RequestMapping("/client/index")
	public String index(Model model, @RequestParam("cid") Optional<Integer> cid) {

		if (cid.isPresent()) {

			List<Product> page = daoProduct.findByCategory(cid.get());
			model.addAttribute("products", page);
		} else {
			List<Product> page = daoProduct.findAll();
			model.addAttribute("products", page);
		}
		// List<Cart> cartItem=cartService.findAll();
		// model.addAttribute("cartItem",cartItem);
		return "index";
	}

	@RequestMapping("/client/cart")
	public String cart(Model model) {
		List<Cart> list = cartService.findAll();
		model.addAttribute("carts", list);
		return "cart";
	}

	@RequestMapping(value = "/client/login/success")
	public String success(Model model, HttpServletResponse response, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String username = userDetails.getUsername();

			Account account = daoAccount.findByUserName(username);

			String id = String.valueOf(account.getId());
			String fullName = account.getFullName();

			if (account != null) {

				// Sanitize values and encode them properly
				String sanitizedFullName = fullName.replaceAll(" ", "_");
				String cleanedUsername = account.getUserName().replaceAll("\\s", "");

				try {
					cleanedUsername = URLEncoder.encode(cleanedUsername, StandardCharsets.UTF_8.toString());
					sanitizedFullName = URLEncoder.encode(sanitizedFullName, StandardCharsets.UTF_8.toString());
					id = URLEncoder.encode(id, StandardCharsets.UTF_8.toString());

					// Set cookies with sanitized and encoded values
					cookieService.setCookie(response, "id", cleanedUsername, 3600);
					cookieService.setCookie(response, "username", id, 3600);
					cookieService.setCookie(response, "fullName", sanitizedFullName, 3600);
					HttpSession session = request.getSession();
					session.setAttribute("AccountSession", account);
					System.out.println("Đăng nhập thành công");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					System.out.println("Lỗi encoding cookie values");
				}
			} else {
				System.out.println("Không tìm thấy tài khoản");
			}
			return "redirect:/client/index";
		}
		return "redirect:/client/index";

	}

	@RequestMapping(value = "/client/signin/error")
	public String loi(Model model) {
		model.addAttribute("loi", "Sai thông tin đăng nhập, Vui lòng nhập lại");
		return "login";
	}

	@RequestMapping("/client/shop")
	public String shop(Model model, @RequestParam("cid") Optional<Integer> cid) {
		System.out.println(cid);
		if (cid.isPresent()) {

			List<Product> page = daoProduct.findByCategory(cid.get());
			model.addAttribute("products", page);
		} else {
			List<Product> page = daoProduct.findAll();
			model.addAttribute("products", page);
		}
		// List<Cart> cartItem=cartService.findAll();
		// model.addAttribute("cartItem",cartItem);
		return "shop";
	}

	public static String formatCurrency(double value) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		return currencyFormat.format(value);

	}
}
