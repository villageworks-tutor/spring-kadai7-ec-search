package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	/**
	 * カテゴリー検索
	 * SELECT * FROM items WHERE category_id = ?
	 */
	List<Item> findByCategoryId(Integer categoryId);

	/**
	 * キーワード検索
	 * SELECT * FROM items WHERE name LIKE ?
	 */
	List<Item> findByNameContaining(String name);

	/**
	 * キーワード検索かつ価格上限値以下検索
	 * SELECT * FROM items WHERE name LIKE ? AND price <= ?
	 */
	List<Item> findByNameContainingAndPriceLessThanEqual(String keyword, Integer maxPrice);

	/**
	 * 価格上限値以下検索
	 * SELECT * FROM items WHERE price <= ?
	 */
	List<Item> findByPriceLessThanEqual(Integer maxPrice);

}
