package com.config;

import com.model.Author;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AuthorWebClient {


    WebClient client = WebClient.create("http://localhost:8080");

    public Mono<Author> get(String id) {
        return client
                .get()
                .uri("/author/" + id)
                .headers(headers -> headers.setBasicAuth("user", "userpwd"))
                .retrieve()
                .bodyToMono(Author.class);
    }

    public Flux<Author> findAll() {
        return client.get()
                .uri("/author")
                .headers(headers -> headers.setBasicAuth("user", "userpwd"))
                .retrieve()
                .bodyToFlux(Author.class);
    }

    public Flux<Author> findByName(String name) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/author")
                        .queryParam("name", name)
                        .build())
                .headers(headers -> headers.setBasicAuth("user", "userpwd"))
                .retrieve()
                .bodyToFlux(Author.class);
    }

    public Mono<Author> save(Author a) {
        return client.post()
                .uri("/author")
                .headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
                .body(Mono.just(a), Author.class)
                .retrieve()
                .bodyToMono(Author.class);
    }

    public Mono<Author> update(Author author) {
        return client
                .put()
                .uri("/author/" + author.getId())
                .headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
                .body(Mono.just(author), Author.class)
                .retrieve()
                .bodyToMono(Author.class);
    }

    public Mono<Void> delete(long id) {
        return client
                .delete()
                .uri("/students/" + id)
                .headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
                .retrieve()
                .bodyToMono(Void.class);

    }
}