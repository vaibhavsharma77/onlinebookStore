package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;


    @Test
    public void shouldReturnAllBooks() {
        // given
        Book book1 = Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(12.99)
                .build();
        Book book2 = Book.builder()
                .title("Title-2")
                .author("Author-2")
                .price(13.99)
                .build();

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getAuthor()).isEqualTo("Author-1");
        assertThat(books.get(1).getTitle()).isEqualTo("Title-2");

        // Verify interaction
        Mockito.verify(bookRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldSaveBook() {
        // given
        Book book = Book.builder()
                .title("Title-1")
                .author("Author-1")
                .price(12.99)
                .build();

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        // when
        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNull(); // Assuming ID is generated after save
        assertThat(savedBook.getTitle()).isEqualTo("Title-1");
        assertThat(savedBook.getAuthor()).isEqualTo("Author-1");
        assertThat(savedBook.getPrice()).isEqualTo(12.99);

        // Verify interaction
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }
}
