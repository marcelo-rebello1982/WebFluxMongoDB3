package com.repository;

import com.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {

    public Flux<Student> findByName(String name);

}