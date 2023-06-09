package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.exception.BadDateRangeException;
import pl.edu.pwr.ztw.books.exception.BookAlreadyRentedException;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.model.dto.RentalDTO;
import pl.edu.pwr.ztw.books.repository.BookRepository;
import pl.edu.pwr.ztw.books.repository.ReaderRepository;
import pl.edu.pwr.ztw.books.repository.RentalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RentalServiceImpl implements IRentalService {
    RentalRepository rentalRepository;
    ReaderRepository readerRepository;
    BookRepository bookRepository;

    public RentalServiceImpl(RentalRepository rentalRepository,
                             ReaderRepository readerRepository,
                             BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Rental> getRentals() {
        List<Rental> rentals = new ArrayList<Rental>();
        rentalRepository.findAll().forEach(rentals::add);
        return rentals;
    }

    @Override
    public Rental getRentalById(long id) {
        Optional<Rental> rentalData = rentalRepository.findById(id);

        if (rentalData.isPresent()) {
            return rentalData.get();
        } else {
            throw new NoSuchElementException("Rental with id '" + id + "' does not exist");
        }
    }

    private boolean isRented(Long bookId) {
        if(rentalRepository.findAllByBookIdAndAndDateReturned(bookId, null).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidDateRange(RentalDTO rentalDTO) throws BadDateRangeException {
        if(rentalDTO.getDateRented().isAfter(rentalDTO.getDateShouldBeReturned())) {
            throw new BadDateRangeException("Date rented is after date should be returned");
        } else if (rentalDTO.getDateReturned() != null && rentalDTO.getDateReturned().isBefore(rentalDTO.getDateRented())) {
            throw new BadDateRangeException("Date returned is before date rented");
        } else {
            return true;
        }
    }

    private void isValidRentalInfo(RentalDTO rentalDTO) {
        Optional<Reader> reader = readerRepository.findById(rentalDTO.getReader().getId());
        Optional<Book> book = bookRepository.findById(rentalDTO.getBook().getId());

        if (!reader.isPresent()) {
            throw new NoSuchElementException("Reader with id '" + rentalDTO.getReader().getId() + "' does not exist");
        }

        if (!book.isPresent()) {
            throw new NoSuchElementException("Book with id '" + rentalDTO.getBook().getId() + "' does not exist");
        }
    }

    @Override
    public Rental createRental(RentalDTO rentalDTO) throws BookAlreadyRentedException, BadDateRangeException {
        isValidRentalInfo(rentalDTO);

        if(!isRented(rentalDTO.getBook().getId()) && isValidDateRange(rentalDTO)) {
            Rental _rental = rentalRepository.save(Rental.builder()
                    .reader(readerRepository.findById(rentalDTO.getReader().getId()).get())
                    .book(bookRepository.findById(rentalDTO.getBook().getId()).get())
                    .dateRented(rentalDTO.getDateRented())
                    .dateReturned(rentalDTO.getDateReturned())
                    .dateShouldBeReturned(rentalDTO.getDateShouldBeReturned())
                    .build());
            return rentalRepository.save(_rental);
        } else {
            throw new BookAlreadyRentedException(rentalDTO.getBook().getId());
        }
    }

    @Override
    public void updateRental(long id, RentalDTO rentalDTO) throws BookAlreadyRentedException {
        Optional<Rental> rentalData = rentalRepository.findById(id);

        if (rentalData.isPresent()) {
            isValidRentalInfo(rentalDTO);

            Rental _rental = rentalData.get();
            _rental.setReader(readerRepository.findById(rentalDTO.getReader().getId()).get());
            _rental.setBook(bookRepository.findById(rentalDTO.getBook().getId()).get());
            _rental.setDateRented(rentalDTO.getDateRented());
            _rental.setDateShouldBeReturned(rentalDTO.getDateShouldBeReturned());
            _rental.setDateReturned(rentalDTO.getDateReturned());
            rentalRepository.save(_rental);
        } else {
            throw new NoSuchElementException("Rental with id '" + id + "' does not exist");
        }
    }

    @Override
    public void deleteRental(long id) throws IllegalOperationException {

            if(rentalRepository.isBlocked(id) == 0)
                rentalRepository.deleteById(id);
            else throw new IllegalOperationException();

    }

    @Override
    public void deleteAllRentals() {
        rentalRepository.deleteAll();
    }

    @Override
    public int getRentalsCount() {
        return (int) rentalRepository.count();
    }
}
