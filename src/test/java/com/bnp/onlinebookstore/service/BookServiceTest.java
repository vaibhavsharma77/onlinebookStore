package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.model.Book;
import com.bnp.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    public void shouldFetchAllBooks(){
        //given
        Book book1=Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(12.99)
                .build();
        Book book2=Book.builder()
                .title("Title-2")
                .author("Author-2")
                .price(13.99)
                .build();
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1,book2));

        //when
        List<Book> books=bookService.findAllBooks();

        //then
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getAuthor()).isEqualTo("Author-1");
    }


    @Test
    public void shouldBeAbleToAddBook(){
        //given
        Book book=Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(12.99)
                .build();
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        //when
        Book books=bookService.addBook(book);
        //then
        assertThat(book.getAuthor()).isEqualTo("Author-1");
    }

    @Test
    public void shouldBeAbleToFindByBookId(){
        //given
        Long bookId=1L;
        Book book=Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(12.99)
                .build();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        //when
        Book books=bookService.fetchBookById(bookId);
        //then
        assertThat(book.getAuthor()).isEqualTo("Author-1");
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFoundById() {
        // given
        Long bookId = 999L;
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException thrown =
                assertThrows(RuntimeException.class, () -> bookService.fetchBookById(bookId));
        assertThat(thrown.getMessage()).isEqualTo("No Book is found for given id");
    }
}
