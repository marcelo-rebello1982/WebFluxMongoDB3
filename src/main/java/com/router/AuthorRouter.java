package com.router;

import com.handler.AuthorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AuthorRouter  {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(AuthorHandler authorHandler) {
        return RouterFunctions
                .route(GET("/author/{id:[0-9]+}").and(accept(APPLICATION_JSON)), authorHandler::findById)
                .andRoute(GET("/author").and(accept(APPLICATION_JSON)), authorHandler::findByName)
                .andRoute(POST("/author").and(accept(APPLICATION_JSON)), authorHandler::save)
                .andRoute(PUT("author/{id:[0-9]+}").and(accept(APPLICATION_JSON)), authorHandler::update)
                .andRoute(DELETE("/author/{id:[0-9]+}").and(accept(APPLICATION_JSON)), authorHandler::delete);
    }
}