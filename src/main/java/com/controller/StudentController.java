package com.controller;

import com.model.Student;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping()
public class StudentController {

    @Autowired
    private StudentService studentService;


    public StudentController() {
    }

    @GetMapping("/student/{id}")
    public Mono<ResponseEntity<Student>> findById(@PathVariable long id) {
        return studentService.findStudentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findAll")
    public Flux<Student> listAll(@RequestParam(name = "name", required = false) String name) {
        return studentService.findByName(name);
    }

    @PostMapping
    @GetMapping("/student")
    public Mono<Student> save(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping("student/{id}")
    public Mono<ResponseEntity<Student>> update(@PathVariable long id, @RequestBody Student student) {
        return studentService.update(id, student)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable long id) {
        return studentService.findStudentById(id)
                .flatMap(s ->
                        studentService.delete(s)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}