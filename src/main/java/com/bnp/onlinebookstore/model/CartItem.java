package com.bnp.onlinebookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CART")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private Long userId;
    private int quantity;

    public CartItem(Book book, Long userId, int quantity) {
        this.book = book;
        this.userId = userId;
        this.quantity = quantity;
    }
}
