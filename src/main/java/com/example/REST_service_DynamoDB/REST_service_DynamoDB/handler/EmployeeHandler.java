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
                        .map(this::createEmplyee)
                        .flatMap(employeeService::createNewCustomer)
                        .flatMap(employee -> ServerResponse.ok().body(BodyInserters.fromValue(employee))));




//        return serverRequest.bodyToMono(JsonNode.class)
//                .doOnNext(jsonNode -> log.info("[POST] Request received to add new Employee: " + jsonNode))
//                .then(ServerResponse.ok().body(BodyInserters.fromValue(Employee.builder()
//                        .employeeId(UUID.randomUUID().toString())
//                        .badgeId(generateNewBadgeNumber())
//                        .employeeFirstName("Rotchy")
//                        .employeeLastName("Moricette")
//                        .employeeEmail("Rotchy0913moricette@gmail.com")
//                        .employeeAddress(
//                                EmployeeAddress.builder()
//                                        .addressLine1("8905 Citrus Village Drive")
//                                        .addressLine2("Apt 302")
//                                        .city("Tampa")
//                                        .stateProvidence("Florida")
//                                        .postalCode("33626")
//                                        .build())
//                        .phoneNumber("754-465-4228")
//                        .hireDate("10/14/2022")
//                        .lineOfBusiness("Marketing")
//                        .managerName("Rishinthan Sivasamy")
//                        .parkingDescription(ParkingDescription.builder()
//                                .parkingDecalNumber(UUID.randomUUID().toString().substring(0,6).toUpperCase())
//                                .employeeVehicle(Vehicle.builder()
//                                        .color("White")
//                                        .make("Toyota")
//                                        .model("Camry")
//                                        .year("2016")
//                                        .plateNumber("GJHV12")
//                                        .build())
//                                .build())
//                        .build())));

//        return ServerResponse.ok().body(BodyInserters.fromValue(
//                Employee.builder()
//                        .employeeId(UUID.randomUUID().toString())
//                        .badgeNumber(generateNewBadgeNumber())
//                        .employeeFirstName("Rotchy")
//                        .employeeLastName("Moricette")
//                        .employeeEmail("Rotchy0913moricette@gmail.com")
//                        .employeeAddress(
//                                EmployeeAddress.builder()
//                                        .addressLine1("8905 Citrus Village Drive")
//                                        .addressLine2("Apt 302")
//                                        .city("Tampa")
//                                        .stateProvidence("Florida")
//                                        .postalCode("33626")
//                                        .build())
//                        .phoneNumber("754-465-4228")
//                        .hireDate("10/14/2022")
//                        .lineOfBusiness("Marketing")
//                        .managerName("Rishinthan Sivasamy")
//                        .parkingDescription(ParkingDescription.builder()
//                                .parkingDecalNumber(UUID.randomUUID().toString().substring(0,6).toUpperCase())
//                                .employeeVehicle(Vehicle.builder()
//                                        .color("White")
//                                        .make("Toyota")
//                                        .model("Camry")
//                                        .year("2016")
//                                        .plateNumber("GJHV12")
//                                        .build())
//                                .build())
//                        .build()
//        ));
    }

    private Employee createEmplyee(JsonNode jsonNode) {
        String newEmployeeID = UUID.randomUUID().toString();
        String newBadgeID = generateNewBadgeNumber();
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

    public String generateNewBadgeNumber(){
        String name = "Moricette";
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return name.charAt(0) + String.format("%06d", number);
    }
}


