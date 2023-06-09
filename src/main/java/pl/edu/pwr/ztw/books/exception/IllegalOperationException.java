package pl.edu.pwr.ztw.books.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class IllegalOperationException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String title = "Illegal operation";

    public IllegalOperationException() {
        super("Operations is illegal");
    }
}
