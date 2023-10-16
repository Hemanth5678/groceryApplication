package com.grocery.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.grocery.enums.AccountStatus;
import com.grocery.enums.DeliveryStatus;
import com.grocery.enums.OrderStatus;
import com.grocery.enums.PaymentStatus;
import com.grocery.enums.Role;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name="order_tbl")
@RequiredArgsConstructor
public class Order {

	public Order(Order order) {
		// TODO Auto-generated constructor stub
		this.id= order.id;
		this.address=order.address;
		this.note=order.note;
		this.totalPrice=order.totalPrice;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	@Id
	private long id;
	private String address;
	private String note;
	private String totalPrice;
	private String deliveryFee;
	private OrderStatus orderStatus;
	private DeliveryStatus deliveryStatus;
	private PaymentStatus paymentStatus;
	private Date orderDate;
	private Date confirmDate;
	private Date finishDate;
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;         // could have used a many to many OR a many to one : middleEntity : one to many with an embedded key, but didn't for simplicity
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}
