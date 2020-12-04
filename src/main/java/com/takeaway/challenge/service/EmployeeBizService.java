package com.takeaway.challenge.service;

import com.sun.jdi.request.InvalidRequestStateException;
import com.takeaway.challenge.command.EmployeeCommand;
import com.takeaway.challenge.kafka.EmployeeEventProducer;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeBizService {

    @Autowired
    EmployeeDataService employeeDataService;

    @Autowired
    DepartmentBizService departmentBizService;

    @Autowired
    EmployeeEventProducer employeeEventProducer;

    private final static String CREATE_TOPIC = "CREATE";
    private final static String UPDATE_TOPIC = "UPDATE";
    private final static String DELETE_TOPIC = "DELETE";

    public Employee create(EmployeeCommand command) throws InvalidRequestStateException{
        if(command.getMailAddress() == null || command.getMailAddress().isEmpty()) {
            throw new InvalidRequestStateException("EmployeeCommand is not valid.");
        }
        Employee employee = employeeFromCommand(command);
        employee = employeeDataService.save(employee);
        employeeEventProducer.produceMessage(CREATE_TOPIC, employee.getUuid().toString());
        return employee;
    }

    public Optional<Employee> findByUuid(UUID employeeUuid) throws InvalidRequestStateException{
        if(employeeUuid == null) {
            throw new InvalidRequestStateException("Employee uuid can not be null.");
        }
        return employeeDataService.findByUuid(employeeUuid);
    }

    public Employee update(EmployeeCommand command) throws InvalidRequestStateException{
        if(command.getUuid() == null) {
            throw new InvalidRequestStateException("Employee uuid can not be null.");
        }

        Optional<Employee> optionalEmployee = employeeDataService.findByUuid(command.getUuid());

        if(!optionalEmployee.isPresent()) {
            throw new InvalidRequestStateException("Employee not found for uuid:" + command.getUuid());
        }

        Employee currentEmployee = optionalEmployee.get();
        currentEmployee.setFirstName(command.getFirstName() != null ? command.getFirstName() : currentEmployee.getFirstName());
        currentEmployee.setLastName(command.getLastName() != null ? command.getLastName() : currentEmployee.getLastName());
        currentEmployee.setMailAddress(command.getMailAddress() != null && !command.getMailAddress().isEmpty() ? command.getMailAddress() : currentEmployee.getMailAddress());
        currentEmployee.setBirthday(command.getBirthday() != null ? command.getBirthday() : currentEmployee.getBirthday());
        if(command.getDepartmentCommand() != null){
            Optional<Department> departmentOptional = departmentBizService.findByName(command.getDepartmentCommand().getName());
            currentEmployee.setDepartment(departmentOptional.get());
        }
        Employee employee = employeeDataService.save(currentEmployee);
        employeeEventProducer.produceMessage(UPDATE_TOPIC, employee.getUuid().toString());
        return employee;
    }

    public void delete(UUID employeeUuid){
        if(employeeUuid == null) {
            throw new InvalidRequestStateException("Employee uuid can not be null.");
        }
        employeeDataService.deleteByUuid(employeeUuid);
        employeeEventProducer.produceMessage(DELETE_TOPIC, employeeUuid.toString());
    }

    private Employee employeeFromCommand(EmployeeCommand command) {
        Employee employee = new Employee();
        employee.setFirstName(command.getFirstName());
        employee.setLastName(command.getLastName());
        employee.setBirthday(command.getBirthday());
        Optional<Department> departmentOptional = departmentBizService.findByName(command.getDepartmentCommand().getName());
        employee.setDepartment(departmentOptional.orElseGet(null));
        employee.setMailAddress(command.getMailAddress());
        return employee;
    }
}
