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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.grocery.enums.AccountStatus;
import com.grocery.enums.Role;

import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	@Id
	private long id;
	private String name;
	private String description;
	private Float price;
	private String imgUrl;
	private Date createdAt;
	private Date updatedAt;
	private String isDeleted;
	private String isInStock;
	private Integer quantity;
	
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    List<Order> orderDetails;
}
