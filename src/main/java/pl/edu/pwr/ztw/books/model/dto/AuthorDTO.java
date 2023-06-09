package pl.edu.pwr.ztw.books.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthorDTO {
    @Schema(description = "Lastname of the author", example = "Tolkien")
    private String lastName;

    @Schema(description = "Firstname of the author", example = "John R. R.")
    private String firstName;
}
