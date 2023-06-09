package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.books.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM book WHERE author_id = ?1")
    Long getAuthorBooks(Long authorId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM rental WHERE book_id = ?1 AND date_returned IS NULL")
    Long isBlocked(Long bookId);
}