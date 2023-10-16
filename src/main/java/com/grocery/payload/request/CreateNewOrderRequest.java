package com.grocery.payload.request;

import java.util.List;
import com.grocery.dto.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNewOrderRequest {
    List<Product> products;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private long totalPrice;
    private long deliveryFee;
    private String paymentId;
}
