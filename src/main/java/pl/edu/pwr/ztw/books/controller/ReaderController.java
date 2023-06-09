package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.dto.ReaderDTO;
import pl.edu.pwr.ztw.books.service.IReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ReaderController {
    IReaderService ReaderService;

    public ReaderController(IReaderService readerService) {
        this.ReaderService = readerService;
    }

    @GetMapping("/readers")
    @Operation(summary = "Gets all readers", description = "Gets all readers which exist in the database")
    public ResponseEntity<List<Reader>> getAllReaders() {
        try {
            List<Reader> readers = ReaderService.getReaders();

            if (readers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(readers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Gets a reader", description = "Gets one reader with " +
            "given id if it exists in the database")
    @GetMapping("/readers/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable("id") long id) {
        Reader reader;

        try {
            reader = ReaderService.getReaderById(id);
            return new ResponseEntity<>(reader, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates and saves a reader", description = "Creates a reader " +
            "and saves it in the database")
    @PostMapping("/readers")
    public ResponseEntity<Reader> createReader(@RequestBody ReaderDTO readerDTO) {
        try {
            Reader _reader = ReaderService.createReader(readerDTO);
            return new ResponseEntity<>(_reader, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Updates a reader", description = "Updates a reader " +
            "if it exists in the database")
    @PutMapping("/readers/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable("id") long id, @RequestBody ReaderDTO readerDTO) {
        Reader readerData;

        try {
            ReaderService.updateReader(id, readerDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deletes a Reader", description = "Deletes a Reader " +
            "if it exists in the database")
    @DeleteMapping("/readers/{id}")
    public ResponseEntity<HttpStatus> deleteReader(@PathVariable("id") long id) {
        try {
            ReaderService.deleteReader(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "Deletes all Readers", description = "Deletes all Readers " +
            "existing in the database")
    @DeleteMapping("/readers")
    public ResponseEntity<HttpStatus> deleteAllReaders() {
        try {
            ReaderService.deleteAllReaders();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Counts all Readers", description = "Counts all Readers " +
            "existing in the database")
    @GetMapping("/readers/count")
    public ResponseEntity<Integer> countAllReaders() {
        int count = ReaderService.getReadersCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
