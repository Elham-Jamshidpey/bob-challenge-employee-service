package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeDataService {

    @Autowired
    EmployeeRepository repository;

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Optional<Employee> findByUuid(UUID employeeUuid) {
        return repository.findById(employeeUuid);
    }

    public void deleteByUuid(UUID employeeUuid){
        repository.deleteById(employeeUuid);
    }
}
