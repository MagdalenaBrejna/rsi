package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.repository.AuthorRepository;
import pl.edu.pwr.ztw.books.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements IAuthorService {
    AuthorRepository authorRepository;
    BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<Author>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public Author getAuthorById(long id) {
        Optional<Author> authorData = authorRepository.findById(id);

        if (authorData.isPresent()) {
            return authorData.get();
        } else {
            throw new NoSuchElementException("Author with id '" + id + "' does not exist");
        }
    }

    @Override
    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.save(Author.builder().lastName(authorDTO.getLastName())
                .firstName(authorDTO.getFirstName()).build());

        return authorRepository.save(author);
    }

    @Override
    public void updateAuthor(long id, AuthorDTO authorDTO) {
        Optional<Author> authorData = authorRepository.findById(id);

        if (authorData.isPresent()) {
            Author _author = authorData.get();
            _author.setLastName(authorDTO.getLastName());
            _author.setFirstName(authorDTO.getFirstName());
            authorRepository.save(_author);
        } else {
            throw new NoSuchElementException("Author with id '" + id + "' does not exist");
        }
    }

    @Override
    public void deleteAuthor(long id) throws IllegalOperationException {

            System.out.println("BOOKS " + bookRepository.getAuthorBooks(id));
            if(bookRepository.getAuthorBooks(id) == 0)
                authorRepository.deleteById(id);
            else
                throw new IllegalOperationException();

    }

    @Override
    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }

    @Override
    public int getAuthorsCount() {
        return authorRepository.findAll().size();
    }
}
