package com.example.REST_service_DynamoDB.REST_service_DynamoDB.router;


import com.example.REST_service_DynamoDB.REST_service_DynamoDB.handler.EmployeeHandler;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Employee;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.EmployeeAddress;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.ParkingDescription;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Vehicle;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.security.UserVerficationFilter;
import com.example.REST_service_DynamoDB.REST_service_DynamoDB.security.VerificationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class EmployeeRouterConfigTest {


    @Mock
    EmployeeHandler employeeHandlereMock;

    UserVerficationFilter verificationFilter = (request, next) -> next.handle(request);

    EmployeeRouterConfig tested;

    Employee mockEmployee;

    @BeforeEach
    void setup(){
        mockEmployee = getEmployee();
        tested = new EmployeeRouterConfig(employeeHandlereMock, verificationFilter);
    }

    @Test
    void returnServer(){

        Mockito.when(employeeHandlereMock.saveEmployee(Mockito.any()))
                .thenReturn(ServerResponse.ok().body(BodyInserters.fromValue(getEmployee())));
        WebTestClient.bindToRouterFunction(tested.employeeRouterFunction()).build().post()
                .uri("/api/v1/employee")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getEmployee()))
                .exchange()
                .expectStatus()
                .isOk();
    }

    private Employee getEmployee() {
        return Employee.builder()
                .employeeId("123456789")
                .badgeId("123456789")
                .employeeFirstName("Bruce")
                .employeeLastName("Wayne")
                .employeeEmail("Bruce.Wayne@mayneenterprise.com")
                .employeeAddress(EmployeeAddress.builder()
                        .addressLine1("56 Batman Way")
                        .city("Gotham")
                        .postalCode("33626")
                        .stateProvidence("New York")
                        .build())
                .phoneNumber("123-123-1234")
                .hireDate("12/24/2022")
                .lineOfBusiness("CEO")
                .parkingDescription(ParkingDescription.builder()
                        .parkingDecalNumber("1234-1234")
                        .employeeVehicle(Vehicle.builder()
                                .plateNumber("GJY701")
                                .make("Mercedez")
                                .model("AMG 1")
                                .color("White")
                                .year("2022")
                                .build())
                        .build())
                .build();
    }


}
