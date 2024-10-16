package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void shouldSaveOrderSuccessfully() {
        // Given
        Order order = Order.builder()
                .userId(1L)
                .totalPrice(100.50)
                .orderDate(LocalDateTime.now())
                .build();

        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        // When
        Order savedOrder = orderRepository.save(order);

        // Then
        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getUserId());
        assertEquals(100.50, savedOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void shouldFindOrderById() {
        // Given
        Order order = Order.builder()
                .id(1L)
                .userId(1L)
                .totalPrice(150.75)
                .orderDate(LocalDateTime.now())
                .build();

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // When
        Optional<Order> foundOrder = orderRepository.findById(1L);

        // Then
        assertTrue(foundOrder.isPresent());
        assertEquals(1L, foundOrder.get().getId());
        assertEquals(150.75, foundOrder.get().getTotalPrice());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldReturnEmptyIfOrderNotFound() {
        // Given
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Order> foundOrder = orderRepository.findById(1L);

        // Then
        assertFalse(foundOrder.isPresent());
        verify(orderRepository, times(1)).findById(1L);
    }
}

