package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Cart;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class OrderController {
	
	@Autowired // seq:41.1
	Cart cart;
	
	@Autowired // seq:43.1
	HttpSession session;
	
	@Autowired // seq:43.2
	CustomerRepository customerRepository;
	
	@Autowired // seq:43.3
	OrderRepository orderRepository;
	
	@Autowired // seq:43.4
	OrderDetailRepository detailRepository;
	
	@GetMapping("/order")      // seq:41.2
	public String index() {
		return "customerForm"; // seq:41.3
	}
	
	@PostMapping("/order/confirm") // sqe:42.1
	public String confirm(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {
		// リクエストパラメータをもとに顧客のインスタンスを生成
		Customer customer = new Customer(name, address, tel, email); // seq:42.2
		// 生成した顧客インスタンスをスコープに登録
		model.addAttribute("customer", customer); // seq:42.3
		// 画面遷移
		return "customerConfirm"; // seq:42.4
	}
	
	@PostMapping("/order") // 43.5
	public String order(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {
		
		// 顧客情報の永続化
		// リクエストパラメータをもとに顧客のインスタンスを生成
		Customer customer = new Customer(name, address, tel, email); // seq:43.6
		// 顧客インスタンスの永続化
		customerRepository.save(customer); // seq:43.7
		
		// 注文永続化
		// リクエストパラメータをもとに注文のインスタンスを生成
		LocalDate today = LocalDate.now();         // seq:43.8
		Integer customerId = customer.getId();     // seq:43.9
		Integer totalPrice = cart.getTotalPrice(); // seq:43.10
		Order order = new Order(customerId, today, totalPrice); // seq:43.11
		// 注文インスタンのインスタンス化
		orderRepository.save(order); // seq:43.12
		
		// 注文明細永続化
		// 注文明細リストの生成
		List<OrderDetail> detailList = new ArrayList<>(); // seq:43.13
		Integer orderId = order.getId();                  // seq:43.14
		// カートの商品数分繰り返す
		for (Item item : cart.getItems()) {
			OrderDetail detail = new OrderDetail(orderId, item.getId(), item.getQuantity()); // seq:43.15
			detailList.add(detail); // seq:43.16
		}
		// 注文明細リストの永続化
		detailRepository.saveAll(detailList);       // seq:43.17
		
		// 注文番号をスコープに登録
		model.addAttribute("orderNumber", orderId); // seq:43.18
		// カートを初期化
		cart.clear();                               // seq:43.19
		
		// 画面遷移
		return "ordered";                           // seq:43.20
	}
	
	
}
