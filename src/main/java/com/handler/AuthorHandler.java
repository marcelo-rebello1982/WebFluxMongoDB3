package com.handler;

import com.model.Author;
import com.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


@Component
public class AuthorHandler {

    private final AuthorService authorService;

    public AuthorHandler(AuthorService authorService) {

        this.authorService = authorService;
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        Mono<Author> authorMono = authorService.findAuthorById(
                serverRequest.pathVariable("id"));
        return authorMono.flatMap(author -> ServerResponse.ok()
                        .body(fromValue(author)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse(null);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authorService.findByName(name), Author.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Author> authorMono = serverRequest.bodyToMono(Author.class);
        return authorMono.flatMap(author ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(authorService.save(author), Author.class));

    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final String authorId = serverRequest.pathVariable("id");
        Mono<Author> authorMono = serverRequest.bodyToMono(Author.class);

        return authorMono.flatMap(author ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(authorService.update(authorId, author), Author.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String authorId = serverRequest.pathVariable("id");
        return authorService
                .findAuthorById(authorId)
                .flatMap(author -> ServerResponse.noContent().build(authorService.delete(author)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}