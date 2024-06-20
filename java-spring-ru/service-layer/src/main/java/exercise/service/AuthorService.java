package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    // BEGIN

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        var authors = authorRepository.findAll();
        return authors.stream()
                .map ( a -> authorMapper.map(a))
                .toList();
    }

    public AuthorDTO getCertainAuthor(Long authorID) {
        return authorMapper.map(authorRepository.findById(authorID).orElseThrow(() -> new ResourceNotFoundException("")));
    }

    public AuthorDTO makeAuthor(AuthorCreateDTO dto) {
        var entity = authorMapper.map(dto);
        authorRepository.save(entity);
        return authorMapper.map(entity);
    }

    public AuthorDTO fixAuthor(AuthorUpdateDTO dto, Long authorId) {
        var entity = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException(("")));
        authorMapper.update(dto,entity);
        authorRepository.save(entity);
        return authorMapper.map(entity);
    }
    // END
}
