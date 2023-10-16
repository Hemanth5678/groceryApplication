package com.grocery.service;

import java.util.List;

import com.grocery.dto.Order;
import com.grocery.exception.BaseException;
import com.grocery.payload.request.CreateNewOrderRequest;

public interface OrderService {
    public String createNewOrder(Order createNewOrderRequest);

    public List<Order> getAllOrdersOfUser(long userId) throws BaseException;

    public Order getOrderWithDetail(Long orderId);
}
