package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;



@Controller
public class CartController {
	
	@Autowired // seq:32.1
	Cart cart;
	
	@Autowired
	ItemRepository itemRepository; // seq:32.2
	
	// カート画面表示
	@GetMapping("/cart") // seq:31.1
	public String index() {
		return "cart";   // seq:31.2
	}
	
	// カートに商品追加
	@PostMapping("/cart/add") // seq:32.3
	public String add(
			@RequestParam(name = "itemId", defaultValue = "") Integer itemId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
		// 商品インスタンスを取得
		Item item = itemRepository.findById(itemId).get(); // seq:32.4
		// 商品の個数を設定
		item.setQuantity(quantity); // seq:32.5
		// 取得した商品インスタンスをセッションスコープのカートに追加
		cart.add(item);             // seq:32.6
		return "redirect:/cart";    // seq:32.7
	}
	
	// カートから商品削除
	@PostMapping("/cart/delete") // seq:33.1
	public String delete(
			@RequestParam(name = "itemId", defaultValue = "") Integer itemId) {
		// 指定された商品をカートから削除
		cart.delete(itemId);     // seq:33.2
		return "redirect:/cart"; // seq:33.3
	}
	
}
