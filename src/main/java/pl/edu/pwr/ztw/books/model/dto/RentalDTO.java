package pl.edu.pwr.ztw.books.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
public class RentalDTO {
    @Schema(description = "Id of reader who borrowed a book", example = "{\"id\": 1}")
    private Reader reader;

    @Schema(description = "Id of book which was borrowed", example = "{\"id\": 1}")
    private Book book;

    @Schema(description = "Date on which book was rented", example = "2022-01-10")
    private LocalDate dateRented;

    @Schema(description = "Date on which book should be returned", example = "2022-02-10")
    private LocalDate dateShouldBeReturned;

    @Schema(description = "Date on which book was returned", example = "2022-01-28")
    private LocalDate dateReturned;
}
