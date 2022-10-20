package com.example.REST_service_DynamoDB.REST_service_DynamoDB.service;

import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
public class EmployeeRepository {

    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<Employee> employeeDynamoDbAsyncTable;

    public EmployeeRepository(DynamoDbEnhancedAsyncClient asyncClient) {
        this.enhancedAsyncClient = asyncClient;
        this.employeeDynamoDbAsyncTable = enhancedAsyncClient.table(Employee.class.getSimpleName(), TableSchema.fromBean(Employee.class));
    }

    //CREATE
    public CompletableFuture<Void> save(Employee employee) {
        return employeeDynamoDbAsyncTable.putItem(employee);
    }

    //READ
    public CompletableFuture<Employee> getCustomerByID(String employeeId) {
        return employeeDynamoDbAsyncTable.getItem(getKeyBuild(employeeId));
    }


    private Key getKeyBuild(String employeeId) {
        return Key.builder().partitionValue(employeeId).build();
    }

}
