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
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier of the reader", example = "1")
    private long id;

    @Schema(description = "Lastname of the reader", example = "Norris")
    private String lastName;

    @Schema(description = "Firstname of the reader", example = "Chuck")
    private String firstName;

    @Schema(description = "Phone number of the reader", example = "+48123456789")
    private String phoneNumber;

    @Builder
    public Reader(long id, String lastName, String firstName, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }
}
