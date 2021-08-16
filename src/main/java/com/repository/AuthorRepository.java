package com.repository;

import com.model.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

    public Flux<Author> findByName(String name);

}