package com.bnp.onlinebookstore.dto;

import com.bnp.onlinebookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    private Long cartItemId;
    private Long bookId;
    private Long userId;
    private int quantity;
}
