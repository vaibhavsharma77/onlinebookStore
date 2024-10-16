package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.model.Order;
import com.bnp.onlinebookstore.repository.CartRepository;
import com.bnp.onlinebookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order checkout(Long userId){
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if(cartItems.isEmpty()){
            throw new RuntimeException("Cart is Empty");
        }

        Double totalPrice = cartItems.stream().map(cartItem -> (cartItem.getQuantity()) * ((cartItem.getBook().getPrice()))).reduce(0.0, Double::sum);
        Order order=Order.builder().
                userId(userId)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .build();
        Order orderInfo = orderRepository.save(order);
        return orderInfo;
    }
}
