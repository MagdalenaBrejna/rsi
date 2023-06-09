package pl.edu.pwr.ztw.books.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class BookAlreadyRentedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String title = "Book already rented";

    public BookAlreadyRentedException(Long bookId) {
        super("Book " + bookId + " is unavailable - already rented");
    }
}
