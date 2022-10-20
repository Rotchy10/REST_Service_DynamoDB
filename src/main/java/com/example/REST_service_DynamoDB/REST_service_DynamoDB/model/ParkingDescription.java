package com.example.REST_service_DynamoDB.REST_service_DynamoDB.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class ParkingDescription {

    private String parkingDecalNumber;

    private Vehicle employeeVehicle;

    @DynamoDbAttribute("parkingDecalNumber")
    public String getParkingDecalNumber() {
        return parkingDecalNumber;
    }

    @DynamoDbAttribute("employeeVehicle")
    public Vehicle getEmployeeVehicle() {
        return employeeVehicle;
    }


}
