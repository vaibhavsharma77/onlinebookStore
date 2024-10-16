package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;


    @Test
    public void shouldRetrieveAllBooks() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        Book book1 = Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(11.99)
                .build();
        Book book2 = Book.builder()
                .title("Title-2")
                .author("Author-2")
                .price(16.99)
                .build();
        //when
        Mockito.when(bookService.findAllBooks()).thenReturn(List.of(book1, book2));
        //then
        mockMvc.perform(get("/api/books/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title-1"));
    }

    @Test
    public void shouldAddBook() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        Book book = Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(11.99)
                .build();
        //when
        Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);
        //then
        ObjectMapper objectMapper = new ObjectMapper();
        String bookContent = objectMapper.writeValueAsString(book);
        mockMvc.perform(post("/api/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookContent)).andExpect(status().isCreated());
    }
    @Test
    public void shouldRetrieveBookByBookId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        //given
        Long bookId=1L;
        Book book = Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(11.99)
                .build();

        //when
        Mockito.when(bookService.fetchBookById(bookId)).thenReturn(book);

        //then
        mockMvc.perform(get("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Title-1"));
    }


    @Test
    public void shouldReturnNotFoundWhenBookDoesNotExistsForGivenBookId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        //given
        Long bookId=420L;

        //when
        Mockito.when(bookService.fetchBookById(bookId)).thenThrow(new RuntimeException("Book does not exists"));

        //then
        mockMvc.perform(get("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
