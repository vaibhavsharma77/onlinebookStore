package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.OrderRequest;
import com.bnp.onlinebookstore.model.Order;
import com.bnp.onlinebookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkoutOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.checkout(orderRequest.getUserId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
