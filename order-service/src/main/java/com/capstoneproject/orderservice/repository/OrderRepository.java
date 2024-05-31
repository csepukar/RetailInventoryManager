package com.capstoneproject.orderservice.repository;

import com.capstoneproject.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserIdAndTypeAndStatus(Long userId,int type, int status );
    List<Order> findAllByStatus(int status);
}
