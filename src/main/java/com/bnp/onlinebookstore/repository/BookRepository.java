package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
