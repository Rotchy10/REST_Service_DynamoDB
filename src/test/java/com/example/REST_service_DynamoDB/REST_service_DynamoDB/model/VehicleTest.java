package com.example.REST_service_DynamoDB.REST_service_DynamoDB.model;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class VehicleTest {

    private final String COLOR = "White";
    private final String MAKE = "Honda";
    private final String YEAR = "2022";
    private final String MODEL = "Civic";
    private final String PLATE = "123456";

    Vehicle tested;

    @BeforeEach
    public void setup(){
        tested = Vehicle.builder()
                .color(COLOR)
                .make(MAKE)
                .year(YEAR)
                .model(MODEL)
                .plateNumber(PLATE)
                .build();
    }

    @Test
    public void vehicleGetterTest(){

        Assertions.assertEquals(COLOR, tested.getColor());
        Assertions.assertEquals(MAKE, tested.getMake());
        Assertions.assertEquals(MODEL, tested.getModel());
        Assertions.assertEquals(YEAR, tested.getYear());
        Assertions.assertEquals(PLATE, tested.getPlateNumber());

    }
}
