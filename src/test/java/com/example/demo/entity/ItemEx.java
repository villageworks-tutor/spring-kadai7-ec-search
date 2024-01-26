package com.example.demo.entity;

public class ItemEx extends Item {

	public ItemEx() {
		super();
	}
	
	public ItemEx(Integer categoryId, String name, Integer price) {
		this.setCategoryId(categoryId);
		this.setName(name);
		this.setPrice(price);
	}
	
	public ItemEx(Integer id, Integer categoryId, String name, Integer price) {
		this(categoryId, name, price);
		this.setId(id);
	}

	public ItemEx(Integer categoryId, String name, Integer price, Integer quantity) {
		this(categoryId, name, price);
		this.setQuantity(quantity);
	}
	
	public ItemEx(Integer id, Integer categoryId, String name, Integer price, Integer quantity) {
		this(id, categoryId, name, price);
		this.setQuantity(quantity);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [");
		builder.append("id=" + this.getId() + ", ");
		builder.append("categoryId=" + this.getCategoryId() + ", ");
		builder.append("name=" + this.getName() + ", ");
		builder.append("price=" + this.getPrice() + ", ");
		builder.append("quantity=" + this.getQuantity() + "]");
		return builder.toString();
	}
	
}
