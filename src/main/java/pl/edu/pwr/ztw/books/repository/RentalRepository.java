package pl.edu.pwr.ztw.books.repository;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.books.model.Rental;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM rental WHERE id = ?1 AND date_returned IS NULL")
    Long isBlocked(Long rentalId);

    @Query(nativeQuery = true, value = "SELECT * FROM rental WHERE book_id = ?1 AND date_returned IS NULL")
    List<Rental> findAllByBookIdAndAndDateReturned(Long bookId, LocalDate date);
}