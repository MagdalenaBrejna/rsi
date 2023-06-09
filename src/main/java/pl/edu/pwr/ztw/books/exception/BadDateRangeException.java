package pl.edu.pwr.ztw.books.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class BadDateRangeException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        private final String title = "Bad date range";

        public BadDateRangeException() {
            super("Bad date range");
        }

        public BadDateRangeException(String message) {
            super(message);
        }

        public BadDateRangeException(String message, Throwable cause) {
            super(message, cause);
        }
}
