package br.com.ifba.backend.services;

import br.com.ifba.backend.dto.BookDTO;
import br.com.ifba.backend.entities.Book;
import br.com.ifba.backend.repositories.BookRepository;
import br.com.ifba.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
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
    public List<BookDTO> findAll() {
        List<Book> list = repository.findAll();
        return list.stream().map(BookDTO::new).toList();
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

}
