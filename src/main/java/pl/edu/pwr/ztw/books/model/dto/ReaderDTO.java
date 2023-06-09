package pl.edu.pwr.ztw.books.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReaderDTO {
    @Schema(description = "Lastname of the reader", example = "Norris")
    private String lastName;

    @Schema(description = "Firstname of the reader", example = "Chuck")
    private String firstName;

    @Schema(description = "Phone number of the reader", example = "+48123456789")
    private String phoneNumber;
}
