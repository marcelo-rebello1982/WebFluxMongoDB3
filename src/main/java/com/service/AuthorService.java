package com.service;

import com.model.Author;
import com.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorService() {
    }

    public Flux<Author> findByName(String name) {
        return (name != null) ? authorRepository.findByName(name) : authorRepository.findAll();
    }

    public Mono<Author> findAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public Mono<Author> save(Author author) {
        return authorRepository.save(author);
    }

    public Mono<Author> update(String id, Author author) {
        return authorRepository.findById(id)
                .flatMap(s -> {
                    author.setId(s.getId());
                    return authorRepository.save(author);
                });

    }

    public Mono<Void> delete(Author author){
        return authorRepository.delete(author);
    }
}