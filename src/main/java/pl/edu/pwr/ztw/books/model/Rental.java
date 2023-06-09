package pl.edu.pwr.ztw.books.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier of the rental", example = "1")
    private long id;

    @Schema(description = "Reader who borrowed a book")
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Schema(description = "Book which was borrowed")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Schema(description = "Date on which book was rented", example = "2022-01-10")
    private LocalDate dateRented;

    @Schema(description = "Date on which book should be returned", example = "2022-02-10")
    private LocalDate dateShouldBeReturned;

    @Schema(description = "Date on which book was returned", example = "2022-01-28")
    private LocalDate dateReturned;

    @Builder
    public Rental(long id, Reader reader, Book book, LocalDate dateRented, LocalDate dateShouldBeReturned, LocalDate dateReturned) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.dateRented = dateRented;
        this.dateShouldBeReturned = dateShouldBeReturned;
        this.dateReturned = dateReturned;
    }
}
