package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.books.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM rental WHERE reader_id = ?1")
    Long isBlocked(Long readerId);
}