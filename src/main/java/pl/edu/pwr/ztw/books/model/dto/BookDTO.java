package pl.edu.pwr.ztw.books.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import pl.edu.pwr.ztw.books.model.Author;

import java.util.List;

@Getter
public class BookDTO {
    @Schema(description = "Title of the book", example = "The Lord of the rings")
    private String title;

    @Schema(description = "Author of the book", example = "[{\"id\": 1}]")
    private Author author;

    @Schema(description = "Number of book's pages", example = "1000")
    private int pages;
}
