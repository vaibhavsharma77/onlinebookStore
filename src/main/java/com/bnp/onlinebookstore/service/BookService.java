package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book fetchBookById(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("No Book is found for given id"));
    }


}
