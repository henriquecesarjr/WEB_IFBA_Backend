package br.com.ifba.backend.dto;

import br.com.ifba.backend.entities.Book;
import jakarta.validation.constraints.NotBlank;

public class BookDTO {

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String title;

    @NotBlank(message = "Campo obrigatório")
    private String author;

    @NotBlank(message = "Campo obrigatório")
    private String isbn;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public BookDTO(Book entity) {
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        isbn = entity.getIsbn();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
