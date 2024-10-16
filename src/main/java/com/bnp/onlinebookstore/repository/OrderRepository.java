package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
