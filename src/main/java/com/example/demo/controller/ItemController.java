package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;


@Controller
public class ItemController {
	
	@Autowired
	CategoryRepository categoryRepository;  // seq:20.1
	
	@Autowired
	ItemRepository itemRepository; 			// seq:20.2
	
	@GetMapping("/items")	// 20.3
	public String index(
			@RequestParam(name = "maxPrice", defaultValue = "") Integer maxPrice,
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			Model model) {
		// カテゴリーリストを取得
		List<Category> categoryList = categoryRepository.findAll(); // seq:20.4
		// 取得したカテゴリーリストをスコープに登録
		model.addAttribute("categoryList", categoryList);			// seq:20.5
		// すべての商品リストを取得
		List<Item> itemList = null;
		if (keyword.isEmpty() && maxPrice == null) {
			// キーワードおよび価格上限が未入力の場合：もとのサンプルショッピングの状況を実現
			if (categoryId == null) {
				itemList = itemRepository.findAll(); // seq:20.6
			} else {
				itemList = itemRepository.findByCategoryId(categoryId); // seq:20.7
			}
		} else {
			if (!keyword.isEmpty()) {
				if (maxPrice == null) {
					// キーワードが入力されかつ価格上限値が未入力の場合
					itemList = itemRepository.findByNameContaining(keyword);
				} else {
					// キーワード・価格上限値ともに入力されている場合
					itemList = itemRepository.findByNameContainingAndPriceLessThanEqual(keyword, maxPrice);
				}
			} else {
				// キーワード未入力かつ価格上限値を入力されている場合
				if (maxPrice != null) {
					itemList = itemRepository.findByPriceLessThanEqual(maxPrice);
				}
			}
		}
		// 取得した商品リストをスコープに登録
		model.addAttribute("itemList", itemList);		// seq:20.8
		model.addAttribute("keyword", keyword);
		model.addAttribute("maxPrice", maxPrice);
		// 取得したカテゴリーリストをスコープに登録
		return "items";									// seq:20.9
	}
	
	@GetMapping("/items/{id}")
	public String show(
			@PathVariable("id") Integer id,
			Model model) {
		// パスパラメータをもとにデータベースから商品を取得
		Item item = itemRepository.findById(id).get();
		// 取得した商品をスコープに登録
		model.addAttribute("item", item);
		// 画面遷移
		return "itemDetail";
	}
	
	
}
