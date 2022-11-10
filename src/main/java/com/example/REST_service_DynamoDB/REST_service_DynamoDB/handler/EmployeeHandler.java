package com.example.REST_service_DynamoDB.REST_service_DynamoDB.handler;

import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Employee;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.EmployeeAddress;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.ParkingDescription;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Vehicle;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeHandler {

    private final ObjectMapper objectMapper;

    private final EmployeeService employeeService;

    public Mono<ServerResponse> saveEmployee(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(JsonNode.class)
                .doOnNext(jsonNode -> log.info("[POST] Request received to add new Employee: " + jsonNode))
                .flatMap(jsonNode -> Mono.just(jsonNode)
                        .map(this::createEmployee)
                        .flatMap(employeeService::saveEmployee)
                        .flatMap(employee -> ServerResponse.ok().body(BodyInserters.fromValue(employee))));
    }

    private Employee createEmployee(JsonNode jsonNode) {
        String newEmployeeID = UUID.randomUUID().toString();
        String newBadgeID = generateNewBadgeNumber(jsonNode.get("employeeLastName").asText());
        String newCarDecal = generateNewParkingDecal();
        Employee new_Employee = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM-dd-yyyy");
        String hireDate = LocalDateTime.now().format(dateTimeFormatter);

        try {
            new_Employee = objectMapper.treeToValue(jsonNode, Employee.class);
            new_Employee.setEmployeeId(newEmployeeID);
            new_Employee.setBadgeId(newBadgeID);
            new_Employee.getParkingDescription().setParkingDecalNumber(newCarDecal);
            new_Employee.setHireDate(hireDate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return new_Employee;
    }

    private String generateNewParkingDecal() {
        return UUID.randomUUID().toString().substring(0,6).toUpperCase();
    }

    public String generateNewBadgeNumber(String employeeLastName){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return employeeLastName.charAt(0) + String.format("%06d", number);
    }
}


