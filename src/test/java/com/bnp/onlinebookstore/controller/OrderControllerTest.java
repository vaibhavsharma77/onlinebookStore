package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.OrderRequest;
import com.bnp.onlinebookstore.model.Order;
import com.bnp.onlinebookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;


    @Test
    public void shouldBeAbleToCheckoutOrder() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        // Given
        OrderRequest request = new OrderRequest();
        request.setUserId(1L);
        Order order = Order.builder()
                .userId(1L)
                .totalPrice(100.00)
                .orderDate(LocalDateTime.now()).
                        build();

        //when
        Mockito.when(orderService.checkout(Mockito.any())).thenReturn(order);

        //Then
        mockMvc.perform(post("/api/orders/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 1}"))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.totalPrice").value(100.00));  // Check that totalPrice is correct
    }
}
