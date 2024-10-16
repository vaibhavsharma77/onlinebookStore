package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.CartRequest;
import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();

    }

    @Test
    public void testAddBookToCart() throws Exception {
        CartRequest cartRequest = new CartRequest(1L, 1L, 1L, 2);
        Book book = new Book(1L, "Sample Title", "Sample Author", 10.99); // Sample book object
        CartItem cartItem = new CartItem(1L, book, 1L, 2);

        when(cartService.addBookToCart(anyLong(), anyLong(), anyInt())).thenReturn(cartItem);
        ObjectMapper mapper=new ObjectMapper();
        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartItem.getId()))
                .andExpect(jsonPath("$.userId").value(cartItem.getUserId()))
                .andExpect(jsonPath("$.quantity").value(cartItem.getQuantity()));

        verify(cartService, times(1)).addBookToCart(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void testUpdateBookQuantity() throws Exception {
        CartRequest cartRequest = new CartRequest(1L, 1L, 1L, 3);
        Book book = new Book(1L, "Sample Title", "Sample Author", 10.99); // Sample book object
        CartItem updatedCartItem = new CartItem(1L, book, 1L, 3);

        when(cartService.updateBookQuantity(any(CartRequest.class))).thenReturn(updatedCartItem);
        ObjectMapper mapper=new ObjectMapper();
        mockMvc.perform(put("/api/cart/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedCartItem.getId()))
                .andExpect(jsonPath("$.userId").value(updatedCartItem.getUserId()))
                .andExpect(jsonPath("$.quantity").value(updatedCartItem.getQuantity()));

        verify(cartService, times(1)).updateBookQuantity(any(CartRequest.class));
    }

    @Test
    public void testRemoveBookFromCart() throws Exception {
        Long bookId = 1L;
        Long userId = 1L;

        doNothing().when(cartService).removeBookFromCart(bookId, userId);

        mockMvc.perform(delete("/api/cart/remove?bookId=" + bookId + "&userId=" + userId))
                .andExpect(status().isNoContent());

        verify(cartService, times(1)).removeBookFromCart(bookId, userId);
    }

    @Test
    public void testGetCartItemsByUserId() throws Exception {
        Long userId = 1L;
        List<CartItem> cartItems = new ArrayList<>();
        Book book1 = new Book(1L, "Sample Title 1", "Sample Author 1", 10.99);
        Book book2 = new Book(2L, "Sample Title 2", "Sample Author 2", 15.99);
        cartItems.add(new CartItem(1L, book1, userId, 2));
        cartItems.add(new CartItem(2L, book2, userId, 1));

        when(cartService.findCartItemsByUserId(userId)).thenReturn(cartItems);

        mockMvc.perform(get("/api/cart/user/" + userId))
                .andExpect(status().isOk());
    }
}
