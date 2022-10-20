package com.example.REST_service_DynamoDB.REST_service_DynamoDB.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class EmployeeAddress
{
    private String addressLine1;

    private String addressLine2;

    private String city;

    private String stateProvidence;

    private String postalCode;

    @DynamoDbAttribute("addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    @DynamoDbAttribute("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    @DynamoDbAttribute("city")
    public String getCity() {
        return city;
    }

    @DynamoDbAttribute("stateProvidence")
    public String getStateProvidence() {
        return stateProvidence;
    }

    @DynamoDbAttribute("postalCode")
    public String getPostalCode() {
        return postalCode;
    }
}
