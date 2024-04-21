package com.capstoneproject.orderservice.repository;

import com.capstoneproject.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
