package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        var authors = bookRepository.findAll();
        return authors.stream()
                .map ( a -> bookMapper.map(a))
                .toList();
    }

    public BookDTO getCertainBook(Long bookID) {
        return bookMapper.map(bookRepository.findById(bookID).orElseThrow(() -> new ResourceNotFoundException("")));
    }

    public BookDTO makeBook(BookCreateDTO dto) {
        var entity = bookMapper.map(dto);
        bookRepository.save(entity);
        return bookMapper.map(entity);
    }

    public BookDTO fixBook(BookUpdateDTO dto, Long authorId) {
        var entity = bookRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException(("")));
        bookMapper.update(dto,entity);
        bookRepository.save(entity);
        return bookMapper.map(entity);
    }
    // END
}
