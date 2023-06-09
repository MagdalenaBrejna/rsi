package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.dto.BookDTO;
import pl.edu.pwr.ztw.books.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    BookRepository bookRepository;

    IAuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, IAuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findByTitleContaining(title).forEach(books::add);

        return books;
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> bookData = bookRepository.findById(id);

        if (bookData.isPresent()) {
            return bookData.get();
        } else {
            throw new NoSuchElementException("Book with id '" + id + "' does not exist");
        }
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Author author = authorService.getAuthorById(bookDTO.getAuthor().getId());

        if (author == null) {
            throw new NoSuchElementException("Author with id '" + bookDTO.getAuthor().getId() + "' does not exist");
        }

        Book _book = bookRepository.save(Book.builder().title(bookDTO.getTitle())
                .pages(bookDTO.getPages()).author(author).build());

        return _book;
    }

    @Override
    public void updateBook(long id, BookDTO bookDTO) {
        Optional<Book> bookData = bookRepository.findById(id);
        Author author = authorService.getAuthorById(bookDTO.getAuthor().getId());

        if (author == null) {
            throw new NoSuchElementException("Author with id '" + bookDTO.getAuthor().getId() + "' does not exist");
        }

        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setTitle(bookDTO.getTitle());
            _book.setPages(bookDTO.getPages());
            _book.setAuthor(author);

            bookRepository.save(_book);
        } else {
            throw new NoSuchElementException("Book with id '" + id + "' does not exist");
        }
    }

    @Override
    public void deleteBook(long id) throws IllegalOperationException {
        if(bookRepository.isBlocked(id) == 0)
            bookRepository.deleteById(id);
        else
            throw new IllegalOperationException();
    }

    @Override
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @Override
    public int getBooksCount() {
        return bookRepository.findAll().size();
    }
}
