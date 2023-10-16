package com.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.dto.Order;
import com.grocery.exception.BaseException;
import com.grocery.payload.request.CreateNewOrderRequest;
import com.grocery.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody Order createNewOrderRequest){
    	return ResponseEntity.status(201).body(orderService.createNewOrder(createNewOrderRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrdersOfUser(@RequestParam(name = "user-id") Long userId) throws BaseException{
    	return ResponseEntity.status(200).body(orderService.getAllOrdersOfUser(userId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getOrderWithDetail(@RequestParam(name = "order-id") Long orderId){
        return ResponseEntity.status(200).body(orderService.getOrderWithDetail(orderId));
    }
}
