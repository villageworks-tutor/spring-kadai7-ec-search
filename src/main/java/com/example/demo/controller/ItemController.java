package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			Model model) {
		// カテゴリーリストを取得
		List<Category> categoryList = categoryRepository.findAll(); // seq:20.4
		// 取得したカテゴリーリストをスコープに登録
		model.addAttribute("categoryList", categoryList);			// seq:20.5
		// すべての商品リストを取得
		List<Item> itemList = null;
		if (categoryId == null) {
			itemList = itemRepository.findAll(); // seq:20.6
		} else {
			itemList = itemRepository.findByCategoryId(categoryId); // seq:20.7
		}
		// 取得した商品リストをスコープに登録
		model.addAttribute("itemList", itemList);		// seq:20.8
		// 取得したカテゴリーリストをスコープに登録
		return "items";									// seq:20.9
	}
}
