package com.takeaway.challenge.service;

import com.takeaway.challenge.controller.DepartmentApiController;
import com.takeaway.challenge.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentDataService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentApiController.class);

    @Autowired
    private DepartmentRepository repository;

    public Department create(Department department) {
        department =  repository.save(department);
        log.info("Department with name " + department.getName() + " created successfully.");
        return department;
    }

    public Optional<Department> findByName(String name) {
        return repository.findByName(name);
    }
}
