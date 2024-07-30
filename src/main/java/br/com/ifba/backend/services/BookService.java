package br.com.ifba.backend.services;

import br.com.ifba.backend.dto.BookDTO;
import br.com.ifba.backend.entities.Book;
import br.com.ifba.backend.repositories.BookRepository;
import br.com.ifba.backend.services.exceptions.DatabaseException;
import br.com.ifba.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(String title, Pageable pageable) {
        Page<Book> list = repository.search(title, pageable);
        return list.map(BookDTO::new);
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id) {
        Optional<Book> obj = repository.findById(id);
        Book entity = obj.orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado")
        );
        return new BookDTO(entity);
    }

    @Transactional
    public BookDTO save(BookDTO dto) {
        Book entity = new Book();
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
        entity = repository.save(entity);
        return new BookDTO(entity);
    }

    @Transactional
    public  BookDTO update(Long id, BookDTO dto) {
        try {
            Book entity = repository.getReferenceById(id);
            entity.setTitle(dto.getTitle());
            entity.setAuthor(dto.getAuthor());
            entity.setIsbn(dto.getIsbn());
            entity = repository.save(entity);
            return new BookDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

}
