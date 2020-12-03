package com.takeaway.challenge.service;

import com.sun.jdi.request.InvalidRequestStateException;
import com.takeaway.challenge.command.DepartmentCommand;
import com.takeaway.challenge.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentBizService {

    @Autowired
    private DepartmentDataService departmentDataService;

    public Department create(DepartmentCommand command) throws InvalidRequestStateException {
        if(command.isValid()) {
            Department department = new Department();
            department.setName(command.getName());
            return departmentDataService.create(department);
        }
        throw new InvalidRequestStateException("Department is invalid!");
    }

    public Optional<Department> findByName(String name){
        Optional<Department> department = departmentDataService.findByName(name);
        return department;
    }

}
