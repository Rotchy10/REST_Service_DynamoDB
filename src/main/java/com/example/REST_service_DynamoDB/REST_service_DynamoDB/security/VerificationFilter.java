package com.example.REST_service_DynamoDB.REST_service_DynamoDB.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class VerificationFilter implements UserVerficationFilter{


    private final String BEARER = "Bearer ";

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM-dd-yyyy");

        String authJWTtoken = request.headers().asHttpHeaders().getFirst("Authorization");
        log.info("[VERIFICATION] Attempting to Authenticate User with token : " + authJWTtoken);

        if(authJWTtoken != null && authJWTtoken.contains(BEARER)){
            authJWTtoken = authJWTtoken.replace(BEARER, "");
            DecodedJWT decodedJWTtoken = null;

            try {
                decodedJWTtoken = JWT.decode(authJWTtoken);
            }catch (RuntimeException e){
                log.error("[VERIFICATION] Failed to decode given JWT Auth0 Token: {}", authJWTtoken);
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).body(BodyInserters.fromValue(ErrorResponse.builder()
                                .httpStatus(HttpStatus.UNAUTHORIZED)
                                .error("UNAUTHRORIZED")
                                .message("Credentials are missing or invalid")
                                .time(LocalDateTime.now().format(dateTimeFormatter))
                        .build()));
            }

            log.info("Received " + decodedJWTtoken.getClaim("name").toString());





        }else {
            log.info("[VERIFICATION]");
        }

        return next.handle(request);
    }
}
