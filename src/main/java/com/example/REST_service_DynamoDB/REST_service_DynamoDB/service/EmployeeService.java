package com.example.REST_service_DynamoDB.REST_service_DynamoDB.service;

import com.example.REST_service_DynamoDB.REST_service_DynamoDB.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<Employee> saveEmployee(Employee employee){
        return Mono.fromFuture(employeeRepository.save(employee))
                .doOnSuccess(e -> log.info("Successfully persisted Employee with id: {} and badge Number: {} ",
                        employee.getEmployeeId(), employee.getBadgeId()))
                .thenReturn(employee);
    }
}
