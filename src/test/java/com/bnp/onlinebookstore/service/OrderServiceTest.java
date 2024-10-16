package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.model.Order;
import com.bnp.onlinebookstore.repository.CartRepository;
import com.bnp.onlinebookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private CartItem cartItem1;
    private CartItem cartItem2;
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        // Create some sample books
        book1 = Book.builder().id(1L).title("Book 1").price(10.00).author("Author 1").build();
        book2 = Book.builder().id(2L).title("Book 2").price(20.00).author("Author 2").build();

        // Create cart items with different quantities
        cartItem1 = new CartItem(book1, 1L, 2); // 2 * 10 = 20.00
        cartItem2 = new CartItem(book2, 1L, 1); // 1 * 20 = 20.00
    }

    @Test
    public void shouldCheckoutSuccessfully_WhenCartIsNotEmpty() {
        // Given
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
        Mockito.when(cartRepository.findByUserId(1L)).thenReturn(cartItems);

        // Total price should be 20.00 (book1) + 20.00 (book2) = 40.00
        Order order = Order.builder()
                .userId(1L)
                .totalPrice(40.00)
                .orderDate(LocalDateTime.now())
                .build();

        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When
        Order savedOrder = orderService.checkout(1L);

        // Then
        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getUserId());
        assertEquals(40.00, savedOrder.getTotalPrice());

        // Verify that methods were called
        verify(cartRepository, times(1)).findByUserId(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void shouldThrowException_WhenCartIsEmpty() {
        // Given
        Mockito.when(cartRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.checkout(1L));
        assertEquals("Cart is Empty", exception.getMessage());

        // Verify cartRepository interaction
        verify(cartRepository, times(1)).findByUserId(1L);
        verify(orderRepository, times(0)).save(any(Order.class));  // No save should happen
    }
}
