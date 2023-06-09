package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.exception.IllegalOperationException;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.dto.ReaderDTO;

import java.util.List;

public interface IReaderService {
    List<Reader> getReaders();
    Reader getReaderById(long id);
    Reader createReader(ReaderDTO readerDTO);
    void updateReader(long id, ReaderDTO readerDTO);
    void deleteReader(long id) throws IllegalOperationException;
    void deleteAllReaders();
    int getReadersCount();
}
