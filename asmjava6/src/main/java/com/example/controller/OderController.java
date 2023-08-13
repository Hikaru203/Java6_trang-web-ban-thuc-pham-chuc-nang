package com.example.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.config.VNPayService;
import com.example.entity.Account;
import com.example.entity.Order;
import com.example.jparepository.AccountRepository;
import com.example.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OderController {
	@Autowired
	OrderService  OderService;
	@Autowired
	AccountRepository accountService;
	
	@Autowired
	private VNPayService vnPayService;

	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return OderService.create(orderData);

	}

	@GetMapping("/client/checkout")
	public String home() {
		return "checkout";
	}

	@PostMapping("/client/submitOrder")
	public String submidOrder(@RequestParam("amount") int orderTotal, @RequestParam("orderInfo") String orderInfo,
			HttpServletRequest request, @CookieValue(value = "username", defaultValue = "0") String userIdCookie,
			@RequestParam("address") String address,@RequestParam("phoneNumber") String phoneNumber) {

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);

		// Lấy thông tin từ form để tạo đơn hàng và lưu vào cơ sở dữ liệu
		Order order = new Order();
		// Convert the user ID from the cookie to an integer
		int userId = Integer.parseInt(userIdCookie);

		// Assuming you have a service to retrieve the Account object by ID
		Account user = accountService.findById(userId).get();

		if (user != null) {
			order.setUser(user);
		} else {
			// Handle the case where the user is not found
			// You might want to redirect to an error page or handle it in some other way
		}
	
		// Lấy các thông tin còn lại từ form
		 // Lấy đối tượng Account từ cơ sở dữ liệu
		order.setUser(user);
		order.setAdress(address);
		order.setPhoneNumber(phoneNumber);
		order.setOrderDate(new Date()); // Ngày hiện tại
		order.setTotalPrice(orderTotal);
		order.setActive(false);
		OderService.save(order);
		return "redirect:" + vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
	public String GetMapping(HttpServletRequest request, Model model) {
		int paymentStatus = vnPayService.orderReturn(request);

		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");
		
		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("paymentTime", paymentTime);
		model.addAttribute("transactionId", transactionId);
		
		return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	}

	@RequestMapping("/cartDetail")
	public String checkOutCart() {
		return "cartDetail";
	}
}
