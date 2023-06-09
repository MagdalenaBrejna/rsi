package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.dto.AuthorDTO;

import java.util.List;

public interface IAuthorService {
    List<Author> getAuthors();
    Author getAuthorById(long id);
    Author createAuthor(AuthorDTO authorDTO);
    void updateAuthor(long id, AuthorDTO authorDTO);
    void deleteAuthor(long id) throws IllegalOperationException;
    void deleteAllAuthors();

    int getAuthorsCount();
}
