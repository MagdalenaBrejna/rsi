package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.dto.BookDTO;
import pl.edu.pwr.ztw.books.service.IBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BookController {
    IBookService IBookService;

    public BookController(IBookService IBookService) {
        this.IBookService = IBookService;
    }

    @GetMapping("/books")
    @Operation(summary = "Gets all books", description = "Gets all books which exist in the database")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
        try {
            List<Book> books = new ArrayList<Book>();

            if (title == null)
                books = IBookService.getBooks();
            else
                IBookService.getBooksByTitle(title).forEach(books::add);

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Gets a book", description = "Gets one book with " +
            "given id if it exists in the database")
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        Book book;

        try {
            book = IBookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates and saves a book", description = "Creates a book " +
            "and saves it in the database")
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        try {
            Book _book = IBookService.createBook(bookDTO);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Updates a book", description = "Updates a book " +
            "if it exists in the database")
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody BookDTO bookDTO) {
        Book bookData;

        try {
            IBookService.updateBook(id, bookDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deletes a book", description = "Deletes a book " +
            "if it exists in the database")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
        try {
            IBookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "Deletes all book", description = "Deletes all book " +
            "existing in the database")
    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            IBookService.deleteAllBooks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Counts all books", description = "Counts all books " +
            "existing in the database")
    @GetMapping("/books/count")
    public ResponseEntity<Integer> countAllBooks() {
        int count = IBookService.getBooksCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}