package com.service;

import com.model.Student;
import com.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentService() {
    }

    public Flux<Student> findByName(String name) {
        return (name != null) ? studentRepository.findByName(name) : studentRepository.findAll();
    }

    public Mono<Student> findStudentById(long id) {
        return studentRepository.findById(id);
    }

    public Mono<Student> save(Student student) {
        return studentRepository.save(student);
    }

    public Mono<Student> update(long id, Student student) {
        return studentRepository.findById(id)
                .flatMap(s -> {
                    student.setId(s.getId());
                    return studentRepository.save(student);
                });

    }

    public Mono<Void> delete(Student student) {
        return studentRepository.delete(student);
    }
}