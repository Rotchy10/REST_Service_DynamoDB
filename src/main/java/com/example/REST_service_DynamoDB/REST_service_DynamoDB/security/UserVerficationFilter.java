package com.example.REST_service_DynamoDB.REST_service_DynamoDB.security;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface UserVerficationFilter extends HandlerFilterFunction<ServerResponse, ServerResponse> {


}
