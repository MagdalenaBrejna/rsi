package pl.edu.pwr.ztw.books.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier of the book", example = "1")
    private long id;
    @Schema(description = "Title of the book", example = "The Lord of the rings")
    private String title;

    @ManyToOne
    @Schema(description = "Author of the book")
    private Author author;

    @Schema(description = "Number of book's pages", example = "1000")
    private int pages;

    @Builder
    public Book(long id, String title, Author author, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }
}
