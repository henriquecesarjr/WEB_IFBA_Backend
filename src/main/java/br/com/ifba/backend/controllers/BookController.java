package br.com.ifba.backend.controllers;

import br.com.ifba.backend.dto.BookDTO;
import br.com.ifba.backend.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService service;

    public BookController(BookService bookService) {
        this.service = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        List<BookDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        BookDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BookDTO> save(@RequestBody BookDTO dto) {
        dto = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
