package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.dto.ReaderDTO;
import pl.edu.pwr.ztw.books.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReaderServiceImpl implements IReaderService {
    ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> getReaders() {
        List<Reader> readers = new ArrayList<Reader>();
        readerRepository.findAll().forEach(readers::add);
        return readers;
    }

    @Override
    public Reader getReaderById(long id) {
        Optional<Reader> readerData = readerRepository.findById(id);

        if (readerData.isPresent()) {
            return readerData.get();
        } else {
            throw new NoSuchElementException("Reader with id '" + id + "' does not exist");
        }
    }

    @Override
    public Reader createReader(ReaderDTO readerDTO) {
        Reader _reader = readerRepository.save(Reader.builder().lastName(readerDTO.getLastName())
                .firstName(readerDTO.getFirstName()).phoneNumber(readerDTO.getPhoneNumber()).build());

        return readerRepository.save(_reader);
    }

    @Override
    public void updateReader(long id, ReaderDTO readerDTO) {
        Optional<Reader> readerData = readerRepository.findById(id);

        if (readerData.isPresent()) {
            Reader _reader = readerData.get();
            _reader.setLastName(readerDTO.getLastName());
            _reader.setFirstName(readerDTO.getFirstName());
            _reader.setPhoneNumber(readerDTO.getPhoneNumber());
            readerRepository.save(_reader);
        } else {
            throw new NoSuchElementException("Reader with id '" + id + "' does not exist");
        }
    }

    @Override
    public void deleteReader(long id) throws IllegalOperationException {
            System.out.print("READER: " + readerRepository.isBlocked(id));
            if(readerRepository.isBlocked(id) == 0)
                readerRepository.deleteById(id);
            else
                throw new IllegalOperationException();

    }

    @Override
    public void deleteAllReaders() {
        readerRepository.deleteAll();
    }

    @Override
    public int getReadersCount() {
        return (int) readerRepository.count();
    }
}
