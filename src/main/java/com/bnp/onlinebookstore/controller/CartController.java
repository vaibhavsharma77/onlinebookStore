package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.CartRequest;
import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addBookToCart(@RequestBody CartRequest cartRequest) {
        CartItem addedItem = cartService.addBookToCart(cartRequest.getBookId(), cartRequest.getUserId(), cartRequest.getQuantity());
        return ResponseEntity.ok(addedItem);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateBookQuantity(@RequestBody CartRequest cartRequest) {
        CartItem updatedItem = cartService.updateBookQuantity(cartRequest);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeBookFromCart(@RequestParam Long bookId, @RequestParam Long userId) {
        cartService.removeBookFromCart(bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.findCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
}
