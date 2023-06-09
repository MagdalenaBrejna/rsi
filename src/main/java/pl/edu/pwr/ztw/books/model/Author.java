package pl.edu.pwr.ztw.books.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier of the author", example = "1")
    private long id;

    @Schema(description = "Lastname of the author", example = "Tolkien")
    private String lastName;

    @Schema(description = "Firstname of the author", example = "John R. R.")
    private String firstName;

    @Builder
    public Author(long id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
