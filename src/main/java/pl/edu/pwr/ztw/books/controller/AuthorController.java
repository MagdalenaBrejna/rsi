package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.service.IAuthorService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AuthorController {
    IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    @Operation(summary = "Gets all authors", description = "Gets all authors which exist in the database")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authors = authorService.getAuthors();

        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Gets an author", description = "Gets one author with " +
            "given id if it exists in the database")
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") long id) {
        Author author;

        try {
            author = authorService.getAuthorById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates and saves a author", description = "Creates an author " +
            "and saves it in the database")
    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDTO authorDTO) {
        Author _author = authorService.createAuthor(authorDTO);
        return new ResponseEntity<>(_author, HttpStatus.CREATED);
    }

    @Operation(summary = "Updates an author", description = "Updates an author " +
            "if it exists in the database")
    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody AuthorDTO authorDTO) {
        Author authorData;

        try {
            authorService.updateAuthor(id, authorDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deletes an author", description = "Deletes an author " +
            "if it exists in the database")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") long id) {
        try {
            authorService.deleteAuthor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Deletes all authors", description = "Deletes all authors " +
            "existing in the database")
    @DeleteMapping("/authors")
    public ResponseEntity<HttpStatus> deleteAllAuthors() {
        try {
            authorService.deleteAllAuthors();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Counts all authors", description = "Counts all authors " +
            "existing in the database")
    @GetMapping("/authors/count")
    public ResponseEntity<Integer> countAuthors() {
        int count = authorService.getAuthorsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
