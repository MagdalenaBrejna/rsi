package pl.edu.pwr.ztw.books.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.dto.BookDTO;
import pl.edu.pwr.ztw.books.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getBooks();
    List<Book> getBooksByTitle(String title);
    Book getBookById(long id);
    Book createBook(BookDTO bookDTO);
    void updateBook(long id, BookDTO bookDTO);
    void deleteBook(long id) throws IllegalOperationException;
    void deleteAllBooks();
    int getBooksCount();
}
