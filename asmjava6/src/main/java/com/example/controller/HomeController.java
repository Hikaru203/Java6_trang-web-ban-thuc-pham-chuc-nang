package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Account;
import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.ProductRepository;
import com.example.service.CartService;
import com.example.service.CookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		return "index";
	}

	@RequestMapping("/client/cart")
	public String cart(Model model) {
		List<Cart> list = cartService.findAll();
		model.addAttribute("carts", list);
		return "cart";
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
	public String loi(Model model) {
		model.addAttribute("loi", "Sai thông tin đăng nhập, Vui lòng nhập lại");
		return "login";
	}

	@RequestMapping(value = "/capnhat")
	public String capnhat(Model model) {
		model.addAttribute("loi", "Sai thông tin đăng nhập, Vui lòng nhập lại");
		return "/capnhat";
	}

	@RequestMapping(value = "/client/update/account", method = RequestMethod.GET)
	public String showUpdateAccountForm(Model model, HttpServletRequest request) {
		// ...
		return "redirect:/capnhat"; // Trả về template để hiển thị form
	}

	@RequestMapping(value = "/client/update/account", method = RequestMethod.POST)
	public String updateAccount(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String fullName, @RequestParam String email, @RequestParam String password) {
		// Đọc giá trị cookie "username"
		Cookie[] cookies = request.getCookies();
		String usernameFromCookie = null;
		for (Cookie cookie : cookies) {
			if ("username".equals(cookie.getName())) {
				usernameFromCookie = cookie.getValue();
				break;
			}
		}

		if (usernameFromCookie != null) {
			// Lấy thông tin tài khoản từ cơ sở dữ liệu dựa trên tên người dùng
			Account account = daoAccount.findByUserName(usernameFromCookie);

			if (account != null) {
				// Thực hiện cập nhật thông tin tài khoản theo yêu cầu
				account.setFullName(fullName);
				account.setEmail(email);
				// Nếu cần cập nhật mật khẩu, hãy xử lý ở đây
				if (!password.isEmpty()) {
					account.setPassword(password);
				}
				// ... cập nhật các thông tin khác của tài khoản

				// Lưu thông tin tài khoản đã cập nhật vào cơ sở dữ liệu
				daoAccount.save(account);

				// Chuyển hướng hoặc trả về phản hồi cho người dùng
				return "redirect:/capnhat";
			} else {
				// Không tìm thấy tài khoản
				return "redirect:/client/index";
			}
		} else {
			// Không có giá trị cookie "username"
			return "redirect:/client/index";
		}
	}

}
