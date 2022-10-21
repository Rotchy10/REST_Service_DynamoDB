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
    private final String ADMINROLE = "internal_administrator_104267_137196";

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

        String authJWTtoken = request.headers().asHttpHeaders().getFirst("Authorization");
        log.info("[VERIFICATION] Attempting to Authenticate User with token : " + authJWTtoken);

        if(authJWTtoken != null && authJWTtoken.contains(BEARER)){
            authJWTtoken = authJWTtoken.replace(BEARER, "");
            DecodedJWT decodedJWTtoken = null;

            try {
                decodedJWTtoken = JWT.decode(authJWTtoken);
            }catch (RuntimeException e){
                log.error("[VERIFICATION] Failed to decode given JWT Auth0 Token: {}", authJWTtoken);
                return createUnauthorizedServerResponse();
            }

            if(decodedJWTtoken.getClaim("role").toString() != null &&
                    decodedJWTtoken.getClaim("name").toString() != null &&
                    decodedJWTtoken.getClaim("departmentCode").toString() != null)
            {
                String roles = decodedJWTtoken.getClaim("role").toString();
                log.info("[VERIFICATION] Received token with Roles: {}", roles);

                if (!roles.contains(ADMINROLE)){
                    return createUnauthorizedServerResponse();
                }else {
                    log.info("[AUTHROIZED] {} authorized with Department Code {}", decodedJWTtoken.getClaim("name").toString()
                            , decodedJWTtoken.getClaim("departmentCode").toString());
                }
            }
            else
            {
                log.info("[VERIFICATION] Given token is missing or invalid.");
                return createUnauthorizedServerResponse();
            }

        }else {
            log.info("[VERIFICATION] Given token is missing or invalid.");
            return createUnauthorizedServerResponse();
        }

        return next.handle(request);
    }

    public Mono<ServerResponse> createUnauthorizedServerResponse(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM-dd-yyyy");

        return ServerResponse.status(HttpStatus.UNAUTHORIZED).body(BodyInserters.fromValue(ErrorResponse.builder()
                .error("UNAUTHRORIZED")
                .message("Credentials are missing or invalid")
                .time(LocalDateTime.now().format(dateTimeFormatter))
                .build()));
    }



}
