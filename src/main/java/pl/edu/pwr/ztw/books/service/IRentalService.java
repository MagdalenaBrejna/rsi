package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.exception.BadDateRangeException;
import pl.edu.pwr.ztw.books.exception.BookAlreadyRentedException;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.model.dto.RentalDTO;

import java.util.List;

public interface IRentalService {
    List<Rental> getRentals();
    Rental getRentalById(long id);
    Rental createRental(RentalDTO rentalDTO) throws BookAlreadyRentedException, BadDateRangeException;
    void updateRental(long id, RentalDTO rentalDTO) throws BookAlreadyRentedException;
    void deleteRental(long id) throws IllegalOperationException;
    void deleteAllRentals();
    int getRentalsCount();
}
