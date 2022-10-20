package com.example.REST_service_DynamoDB.REST_service_DynamoDB.router;

import com.example.REST_service_DynamoDB.REST_service_DynamoDB.handler.EmployeeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class EmployeeRouterConfig {

    private final EmployeeHandler employeeHandler;

    @Bean
    public RouterFunction<ServerResponse> employeeRouterFunction(){
        return RouterFunctions.route()
                .path("/api/v1/employee", builder -> builder
                        .POST("", accept(MediaType.APPLICATION_JSON), employeeHandler::saveEmployee))
                .build();
    }






}
