package com.router;

import com.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> route(StudentHandler studentHandler) {
        return RouterFunctions
                .route(
                        GET("/students/{id:[0-9]+}")
                                .and(accept(APPLICATION_JSON)), studentHandler::findById)
                .andRoute(
                        GET("/students")
                                .and(accept(APPLICATION_JSON)), studentHandler::findAll)
                .andRoute(
                        POST("/students")
                                .and(accept(APPLICATION_JSON)), studentHandler::save)
                .andRoute(
                        PUT("students/{id:[0-9]+}")
                                .and(accept(APPLICATION_JSON)), studentHandler::update)
                .andRoute(
                        DELETE("/students/{id:[0-9]+}")
                                .and(accept(APPLICATION_JSON)), studentHandler::delete);
    }
}