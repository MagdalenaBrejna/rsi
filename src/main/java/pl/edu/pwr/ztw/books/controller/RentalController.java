package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.exception.BadDateRangeException;
import pl.edu.pwr.ztw.books.exception.BookAlreadyRentedException;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.model.dto.RentalDTO;
import pl.edu.pwr.ztw.books.service.IRentalService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RentalController {
    IRentalService rentalService;

    public RentalController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/rentals")
    @Operation(summary = "Gets all rentals", description = "Gets all rentals which exist in the database")
    public ResponseEntity<List<Rental>> getAllRentals() {
        try {
            List<Rental> Rentals = rentalService.getRentals();

            if (Rentals.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Rentals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Gets a rental", description = "Gets one rental with " +
            "given id if it exists in the database")
    @GetMapping("/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") long id) {
        Rental Rental;

        try {
            Rental = rentalService.getRentalById(id);
            return new ResponseEntity<>(Rental, HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates and saves a rental", description = "Creates a rental " +
            "and saves it in the database")
    @PostMapping("/rentals")
    public ResponseEntity<Rental> createRental(@RequestBody RentalDTO RentalDTO) {
        try {
            return new ResponseEntity<>(rentalService.createRental(RentalDTO), HttpStatus.CREATED);
        } catch (BookAlreadyRentedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Updates a Rental", description = "Updates a Rental " +
            "if it exists in the database")
    @PutMapping("/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable("id") long id, @RequestBody RentalDTO RentalDTO) throws BookAlreadyRentedException {
        rentalService.updateRental(id, RentalDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Deletes a rental", description = "Deletes a rental " +
            "if it exists in the database")
    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<HttpStatus> deleteRental(@PathVariable("id") long id) {
        try {
            rentalService.deleteRental(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "Deletes all Rentals", description = "Deletes all Rentals " +
            "existing in the database")
    @DeleteMapping("/rentals")
    public ResponseEntity<HttpStatus> deleteAllRentals() {
        try {
            rentalService.deleteAllRentals();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Counts all Rentals", description = "Counts all Rentals " +
            "existing in the database")
    @GetMapping("/rentals/count")
    public ResponseEntity<Integer> countAllRentals() {
        int count = rentalService.getRentalsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
