package com.handler;

import com.model.Student;
import com.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


@Component
public class StudentHandler {

    private StudentService studentService;

    public StudentHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        Mono<Student> studentMono = studentService.findStudentById(
                Long.parseLong(serverRequest.pathVariable("id")));
        return studentMono.flatMap(student -> ServerResponse.ok()
                        .body(fromValue(student)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse(null);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findByName(name), Student.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Student> studentMono = serverRequest.bodyToMono(Student.class);
        return studentMono.flatMap(student ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.save(student), Student.class));

    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        final long studentId = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<Student> studentMono = serverRequest.bodyToMono(Student.class);

        return studentMono.flatMap(student ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.update(studentId, student), Student.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        final long studentId = Long.parseLong(serverRequest.pathVariable("id"));
        return studentService
                .findStudentById(studentId)
                .flatMap(s -> ServerResponse.noContent().build(studentService.delete(s)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}