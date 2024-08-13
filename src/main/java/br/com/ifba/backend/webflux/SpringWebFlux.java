package br.com.ifba.backend.webflux;

import br.com.ifba.backend.dto.BookDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SpringWebFlux {

    private final WebClient webClient;

    public SpringWebFlux(WebClient.Builder webClientBuilder, @Value("${books.api.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<BookDTO> saveBook(BookDTO dto) {
        return webClient.post()
                .uri("/save")
                .body(Mono.just(dto), BookDTO.class)
                .retrieve()
                .bodyToMono(BookDTO.class);
    }

    public Mono<BookDTO> updateBook(Long id, BookDTO dto) {
        return webClient.put()
                .uri("/update/{id}", id)
                .body(Mono.just(dto), BookDTO.class)
                .retrieve()
                .bodyToMono(BookDTO.class);
    }

    public Mono<BookDTO> getBookById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(BookDTO.class);
    }

    public Mono<BookDTO> getAllBooks(int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(BookDTO.class);
    }

    public Mono<String> deleteBook(Long id) {
        return webClient.delete()
                .uri("/delete/{id}", id)
                .retrieve()
                .bodyToMono(String.class);
    }

}
