package com.example.REST_service_DynamoDB.REST_service_DynamoDB.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {

    private String employeeId;

    private String badgeId;

    private String employeeFirstName;

    private String employeeLastName;

    private String employeeEmail;

    private EmployeeAddress employeeAddress;

    private String phoneNumber;

    private String hireDate;

    private String terminationDate;

    private String lineOfBusiness;

    private String managerName;

    private ParkingDescription parkingDescription;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("employeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    @DynamoDbAttribute("badgeId")
    public String getBadgeId() {
        return badgeId;
    }

    @DynamoDbAttribute("employeeFirstName")
    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    @DynamoDbAttribute("employeeLastName")
    public String getEmployeeLastName() {
        return employeeLastName;
    }

    @DynamoDbAttribute("employeeEmail")
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    @DynamoDbAttribute("employeeAddress")
    public EmployeeAddress getEmployeeAddress() {
        return employeeAddress;
    }

    @DynamoDbAttribute("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @DynamoDbAttribute("hireDate")
    public String getHireDate() {
        return hireDate;
    }

    @DynamoDbAttribute("terminationDate")
    public String getTerminationDate() {
        return terminationDate;
    }

    @DynamoDbAttribute("lineOfBusiness")
    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    @DynamoDbAttribute("managerName")
    public String getManagerName() {
        return managerName;
    }

    @DynamoDbAttribute("parkingDescription")
    public ParkingDescription getParkingDescription() {
        return parkingDescription;
    }
}
