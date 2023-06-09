package pl.edu.pwr.ztw.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BookAlreadyRentedException.class)
    public ResponseEntity<?> rentedBookException(BookAlreadyRentedException exception,
                                                 WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title(exception.getTitle())
                .source(request.getDescription(false))
                .details(exception.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadDateRangeException.class)
    public ResponseEntity<?> badDateRangeException(BadDateRangeException exception,
                                                   WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title(exception.getTitle())
                .source(request.getDescription(false))
                .details(exception.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception exception, WebRequest request) {

        ApiException apiException = ApiException.builder()
                .title("Entity does not exist")
                .source(request.getDescription(false))
                .details(exception.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
