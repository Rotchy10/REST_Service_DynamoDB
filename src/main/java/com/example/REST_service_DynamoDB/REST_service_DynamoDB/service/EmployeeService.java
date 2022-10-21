package com.example.REST_service_DynamoDB.REST_service_DynamoDB.service;

import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<Employee> saveEmployee(Employee employee){
        return Mono.fromFuture(employeeRepository.save(employee))
                .thenReturn(employee);
    }
}
