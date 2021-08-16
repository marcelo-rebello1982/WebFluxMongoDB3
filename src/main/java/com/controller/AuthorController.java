package com.controller;

import com.model.Author;
import com.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping()
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/findByName")
    public Flux<Author> findByName(@RequestParam(name = "name", required = false) String name) {
        return authorService.findByName(name);
    }

    @GetMapping("/author/{id}")
    public Mono<ResponseEntity<Author>> findById(@PathVariable String id) {
        return authorService.findAuthorById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @GetMapping("/author")
    public Mono<Author> save(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PutMapping("author/{id}")
    public Mono<ResponseEntity<Author>> update(@PathVariable String id, @RequestBody Author author) {
        return authorService.update(id, author)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable String id) {
        return authorService.findAuthorById(id)
                .flatMap(s ->
                        authorService.delete(s)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}