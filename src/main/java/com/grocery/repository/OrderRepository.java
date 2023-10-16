package com.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.dto.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findAllByUserId(Long userId);
}
