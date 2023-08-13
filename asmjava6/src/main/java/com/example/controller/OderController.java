package com.example.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.config.VNPayService;
import com.example.entity.Account;
import com.example.entity.AddressDistrict;
import com.example.entity.Cart;
import com.example.entity.Order;
import com.example.entity.OrderDetail;
import com.example.jparepository.AccountRepository;
import com.example.jparepository.OrderRepository;
import com.example.service.OderService;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.service.AddressDistrictService;
import com.example.service.CartService;
import com.example.service.OderDetailService;

@Controller
public class OderController {
	@Autowired
	OderService OderService;
	@Autowired
	AccountRepository accountService;
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OderDetailService detailService;

	@Autowired
	CartService cartService;

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
			@RequestParam("address") String address, @RequestParam("phoneNumber") String phoneNumber) {

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
		HttpSession session = request.getSession();
		Account AccountSession = (Account) session.getAttribute("AccountSession");

		// Lấy thông tin từ form để tạo đơn hàng và lưu vào cơ sở dữ liệu
		Order order = new Order();
		// Assuming you have a service to retrieve the Account object by ID
		Account user = accountService.findById(AccountSession.getId()).get();

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
		session.setAttribute("orderId", order.getId());
		return "redirect:" + vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
	public String getVnpayPayment(HttpServletRequest request, Model model) {
		int paymentStatus = vnPayService.orderReturn(request);
		HttpSession session = request.getSession();
		Account AccountSession = (Account) session.getAttribute("AccountSession");
		String orderInfo = AccountSession.getFullName();

		String paymentTimeString = request.getParameter("vnp_PayDate");
		String Txnref = request.getParameter("vnp_TxnRef");
		String totalPrice = request.getParameter("vnp_Amount");

		double totalAmount = Double.parseDouble(String.valueOf(Double.valueOf(totalPrice) / 100));
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat currencyFormatter = new DecimalFormat("###,###,### VND");

		String formattedTotalAmount = currencyFormatter.format(totalAmount);

		// Chuyển đổi chuỗi thời gian sang đối tượng LocalDateTime
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime paymentTime = LocalDateTime.parse(paymentTimeString, inputFormatter);

		// Định dạng thời gian sang "dd/MM/yyyy HH:mm:ss"
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedPaymentTime = paymentTime.format(outputFormatter);

		Integer orderIdObj = (Integer) session.getAttribute("orderId");

		if (orderIdObj != null) {
			if (paymentStatus == 1) {
				int orderId = orderIdObj.intValue();
				OderService.setActiveForOrder(orderId);
//				Cart cartIdObj = cartService.findById(cartId);
//				OrderDetail detail = new OrderDetail();
//				detail.setOrder(orderdObj); // Sử dụng orderId lấy từ session
//				detail.setCart(cartIdObj);
//				detail.setOrdresCode(Txnref);
//
//				detailService.save(detail);
			}
		} else {
			// Xử lý trường hợp không tìm thấy orderId trong session
		}
		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", formattedTotalAmount);
		model.addAttribute("paymentTime", formattedPaymentTime);
		model.addAttribute("Txnref", Txnref);

		return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	}

	@RequestMapping("/cartDetail")
	public String checkOutCart() {
		return "cartDetail";
	}
}
