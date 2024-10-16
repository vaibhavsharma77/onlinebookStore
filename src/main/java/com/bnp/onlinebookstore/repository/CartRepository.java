package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByUserIdAndBookId(Long userId, Long bookId);
    List<CartItem> findByUserId(Long userId);
}
