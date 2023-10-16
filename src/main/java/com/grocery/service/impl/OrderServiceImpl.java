package com.grocery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.grocery.dto.Order;
import com.grocery.dto.User;
import com.grocery.exception.BadRequestException;
import com.grocery.exception.BaseException;
import com.grocery.repository.OrderRepository;
import com.grocery.service.AuthService;
import com.grocery.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AuthService authService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductServiceImpl productServiceImpl;


    @Override
    public List<Order> getAllOrdersOfUser(long userId) throws BaseException{
        User user = authService.getUserLoggedIn();
        if (user.getId() != userId) {
            throw new BadRequestException("You don't have permission.");
        }
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders;
    }

    @Override
    public Order getOrderWithDetail(Long orderId){
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        return orderOptional.get();
    }
    
    @Override
    public String createNewOrder(Order order)
            {
        User user = authService.getUserLoggedIn();
        order.setUser(user);

        orderRepository.save(order);
        return "Ordered successfully.";
    }

}
