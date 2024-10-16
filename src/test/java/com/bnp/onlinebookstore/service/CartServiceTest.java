package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.dto.CartRequest;
import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.model.CartItem;
import com.bnp.onlinebookstore.repository.BookRepository;
import com.bnp.onlinebookstore.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CartService cartService;

    private CartItem dummyCartItem;
    private Book dummyBook;

    @BeforeEach
    public void setUp() {
        dummyBook = new Book();
        dummyBook.setId(1L);
        dummyBook.setTitle("Sample Book");
        dummyBook.setAuthor("Author Name");
        dummyBook.setPrice(19.99); // Set other necessary fields for the Book object

        dummyCartItem = new CartItem(dummyBook, 1L, 2);
        dummyCartItem.setId(1L); // Set the ID for the cart item
    }

    @Test
    public void shouldUpdateBookQuantity() {
        // given
        CartRequest cartRequest = new CartRequest(1L, 1L, 1L, 5); // Updated CartRequest
        when(cartRepository.findById(1L)).thenReturn(Optional.of(dummyCartItem));
        when(cartRepository.save(any(CartItem.class))).thenReturn(dummyCartItem);

        // when
        CartItem updatedCartItem = cartService.updateBookQuantity(cartRequest);

        // then
        assertNotNull(updatedCartItem);
        assertEquals(5, updatedCartItem.getQuantity());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(dummyCartItem);
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistentCartItem() {
        // given
        CartRequest cartRequest = new CartRequest(1L, 1L, 1L, 5); // Updated CartRequest
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.updateBookQuantity(cartRequest);
        });

        assertEquals("Item not found in cart", exception.getMessage());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, never()).save(any(CartItem.class));
    }

    @Test
    public void shouldThrowExceptionWhenUserNotAuthorizedToUpdate() {
        // given
        CartRequest cartRequest = new CartRequest(1L, 1L, 2L, 5); // Different userId
        when(cartRepository.findById(1L)).thenReturn(Optional.of(dummyCartItem));

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.updateBookQuantity(cartRequest);
        });

        assertEquals("User not authorized to update this item", exception.getMessage());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, never()).save(any(CartItem.class));
    }

    @Test
    public void shouldAddBookToCart() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(dummyBook));
        when(cartRepository.save(any(CartItem.class))).thenReturn(dummyCartItem);

        // when
        CartItem addedCartItem = cartService.addBookToCart(1L, 1L, 2);

        // then
        assertNotNull(addedCartItem);
        assertEquals(dummyBook.getId(), addedCartItem.getBook().getId());
        assertEquals(1L, addedCartItem.getUserId());
        assertEquals(2, addedCartItem.getQuantity());
        verify(bookRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void shouldThrowExceptionWhenAddingNonExistentBook() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.addBookToCart(1L, 1L, 2);
        });

        assertEquals("Unable to add Item in the cart", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(cartRepository, never()).save(any(CartItem.class));
    }

    @Test
    public void shouldRemoveBookFromCart() {
        // given
        when(cartRepository.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.of(dummyCartItem));

        // when
        cartService.removeBookFromCart(1L, 1L);

        // then
        verify(cartRepository, times(1)).findByUserIdAndBookId(1L, 1L);
        verify(cartRepository, times(1)).delete(dummyCartItem);
    }

    @Test
    public void shouldThrowExceptionWhenRemovingNonExistentCartItem() {
        // given
        when(cartRepository.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.removeBookFromCart(1L, 1L);
        });

        assertEquals("Item not found in cart", exception.getMessage());
        verify(cartRepository, times(1)).findByUserIdAndBookId(1L, 1L);
        verify(cartRepository, never()).delete(any(CartItem.class));
    }
}
