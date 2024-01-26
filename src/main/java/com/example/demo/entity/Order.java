package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "customer_id")
	private Integer customerId;
	@Column(name = "ordered_on")
	private LocalDate orderedOn;
	@Column(name = "total_price")
	private Integer tptalPrice;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public Order() {}

	/**
	 * コンストラクション
	 * @param customerId
	 * @param orderedOn
	 * @param tptalPrice
	 */
	public Order(Integer customerId, LocalDate orderedOn, Integer tptalPrice) {
		this.customerId = customerId;
		this.orderedOn = orderedOn;
		this.tptalPrice = tptalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDate getOrderedOn() {
		return orderedOn;
	}

	public void setOrderedOn(LocalDate orderedOn) {
		this.orderedOn = orderedOn;
	}

	public Integer getTptalPrice() {
		return tptalPrice;
	}

	public void setTptalPrice(Integer tptalPrice) {
		this.tptalPrice = tptalPrice;
	}
	
}
