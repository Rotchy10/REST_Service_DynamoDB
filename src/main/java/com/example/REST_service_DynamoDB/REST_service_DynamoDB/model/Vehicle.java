package com.example.REST_service_DynamoDB.REST_service_DynamoDB.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDbBean
public class Vehicle {

    private String color;

    private String make;

    private String model;

    private String year;

    private String plateNumber;

    @DynamoDbAttribute("color")
    public String getColor() {
        return color;
    }

    @DynamoDbAttribute("make")
    public String getMake() {
        return make;
    }

    @DynamoDbAttribute("model")
    public String getModel() {
        return model;
    }

    @DynamoDbAttribute("year")
    public String getYear() {
        return year;
    }

    @DynamoDbAttribute("plateNumber")
    public String getPlateNumber() {
        return plateNumber;
    }


}
