package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Item;

@Component
@SessionScope
public class Cart {
	
	/**
	 * フィールド：商品リスト
	 */
	private List<Item> items = new ArrayList<>();

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * 商品を追加する
	 * @param target 追加する商品
	 */
	public void add(Item target) {
		// 追加する商品がカートの商品リストの要素である場合その要素を格納する変数
		Item existed = null;
		// 商品リストを走査
		for (Item item : this.items) {
			// 商品番号による追加する商品と商品リストの要素との比較
			if (item.getId() == target.getId()) {
				// 一致した場合：存在したものとして要素を格納して走査を離脱
				existed = item;
				break;
			}
		}
		
		if (existed == null) {
			// 追加商品が商品リストに見つからなかった場合：商品リストに追加
			this.items.add(target);
		} else {
			// 追加商品が商品リストに見つかった場合：数量のみを変更
			Integer quantity = target.getQuantity() + existed.getQuantity();
			existed.setQuantity(quantity);
		}
	}

	/**
	 * カート内の商品の金額の総合計を計算する
	 * @return カート内商品の金額の総合計
	 */
	public Integer getTotalPrice() {
		Integer totalPrice = 0;
		for (Item item : this.items) {
			totalPrice += item.getPrice() * item.getQuantity();
		}
		return totalPrice;
	}

	/**
	 * カート内の商品を削除する
	 * @param id 削除する商品の商品番号
	 */
	public void delete(Integer id) {
		for (Item item : this.items) {
			if (item.getId() == id) {
				this.items.remove(item);
				break;
			}
		}
	}

	/**
	 * カート内の商品をクリアする
	 */
	public void clear() {
		this.items.clear();
	}
	
}
