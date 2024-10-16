package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> fetchAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") Long bookId){
        try{
            Book book = bookService.fetchBookById(bookId);
            return new ResponseEntity<>(book,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
