package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.dto.CartRequest;
import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.repository.BookRepository;
import com.bnp.onlinebookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;

    public CartItem addBookToCart(Long bookId, Long userId, int quantity) {
        Optional<Book> bookInfo = bookRepository.findById(bookId);
        if(bookInfo.isPresent()) {
            Book book = bookInfo.get();
            CartItem cartItem = new CartItem(book, userId, quantity);
            return cartRepository.save(cartItem);
        }
        throw new RuntimeException("Unable to add Item in the cart");
    }

    public CartItem updateBookQuantity(CartRequest cartRequest) {
        CartItem cartItem = cartRepository.findById(cartRequest.getCartItemId())
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        // Optional: Validate userId if necessary
        if (!cartItem.getUserId().equals(cartRequest.getUserId())) {
            throw new RuntimeException("User not authorized to update this item");
        }

        cartItem.setQuantity(cartRequest.getQuantity());
        return cartRepository.save(cartItem);
    }

    public void removeBookFromCart(Long bookId, Long userId) {
        CartItem cartItem = cartRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartRepository.delete(cartItem);
    }

    public List<CartItem> findCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

