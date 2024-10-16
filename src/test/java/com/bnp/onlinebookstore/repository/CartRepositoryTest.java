package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.model.CartItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @Test
    public void shouldFindCartItemByUserIdAndBookId() {
        // given
        Book book = Book.builder().title("Book Title").author("Author").price(9.99).build();
        CartItem cartItem = CartItem.builder()
                .book(book)
                .userId(1L)
                .quantity(2)
                .build();

        when(cartRepository.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.of(cartItem));

        // when
        Optional<CartItem> foundCartItem = cartRepository.findByUserIdAndBookId(1L, 1L);

        // then
        Assertions.assertThat(foundCartItem).isPresent();
        Assertions.assertThat(foundCartItem.get().getUserId()).isEqualTo(1L);
        Assertions.assertThat(foundCartItem.get().getQuantity()).isEqualTo(2);

        // Verify interaction
        Mockito.verify(cartRepository, Mockito.times(1)).findByUserIdAndBookId(1L, 1L);
    }

    @Test
    public void shouldReturnEmptyIfCartItemNotFound() {
        // given
        when(cartRepository.findByUserIdAndBookId(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(Optional.empty());

        // when
        Optional<CartItem> foundCartItem = cartRepository.findByUserIdAndBookId(1L, 1L);

        // then
        Assertions.assertThat(foundCartItem).isEmpty();

        // Verify interaction
        Mockito.verify(cartRepository, Mockito.times(1)).findByUserIdAndBookId(1L, 1L);
    }

    @Test
    public void shouldFindAllCartItemsByUserId() {
        // given
        Book book1 = Book.builder().title("Book Title 1").author("Author 1").price(9.99).build();
        Book book2 = Book.builder().title("Book Title 2").author("Author 2").price(15.99).build();

        CartItem cartItem1 = CartItem.builder().book(book1).userId(1L).quantity(2).build();
        CartItem cartItem2 = CartItem.builder().book(book2).userId(1L).quantity(1).build();

        List<CartItem> cartItems= List.of(cartItem1,cartItem2);

        when(cartRepository.findByUserId(1L)).thenReturn(cartItems);

        // when
        List<CartItem> foundCartItems = cartRepository.findByUserId(1L);

        // then
        Assertions.assertThat(foundCartItems).hasSize(2);
        Assertions.assertThat(foundCartItems.get(0).getBook().getTitle()).isEqualTo("Book Title 1");
        Assertions.assertThat(foundCartItems.get(1).getBook().getTitle()).isEqualTo("Book Title 2");

        // Verify interaction
        Mockito.verify(cartRepository, Mockito.times(1)).findByUserId(1L);
    }

    @Test
    public void shouldReturnEmptyListIfNoCartItemsFoundForUser() {
        // given
        when(cartRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        // when
        List<CartItem> foundCartItems = cartRepository.findByUserId(1L);

        // then
        Assertions.assertThat(foundCartItems).isEmpty();

        // Verify interaction
        Mockito.verify(cartRepository, Mockito.times(1)).findByUserId(1L);
    }
}
