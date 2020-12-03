package com.takeaway.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.takeaway.challenge.command.DepartmentCommand;
import com.takeaway.challenge.command.EmployeeCommand;
import com.takeaway.challenge.service.EmployeeBizService;
import io.swagger.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-12-03T17:19:31.465Z")

@Controller
public class EmployeeApiController implements EmployeeApi {

    @Autowired
    private EmployeeBizService employeeBizService;

    private static final Logger log = LoggerFactory.getLogger(EmployeeApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public EmployeeApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addEmployee(@ApiParam(value = "Employee object that needs to be added to the system" ,required=true )  @Valid @RequestBody Employee body) {
        String accept = request.getHeader("Accept");
        try {
            employeeBizService.create(createEmployeeCommandFromBody(body));
        } catch (Exception e){
            log.error("Error on creating an employee : " ,e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEmployee(@ApiParam(value = "Employee uuid to delete",required=true) @PathVariable("employeeUuid") String employeeUuid,@ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
        try {
            employeeBizService.delete(UUID.fromString(employeeUuid));
        } catch (Exception e){
            log.error("Error on delete an employee : " ,e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Employee> getEmployeeByUuid(@ApiParam(value = "UUID of employee to return",required=true) @PathVariable("employeeUuid") String employeeUuid) {
        Optional<com.takeaway.challenge.model.Employee> optionalEmployee = employeeBizService.findByUuid(UUID.fromString(employeeUuid));

        if (optionalEmployee.isPresent()) {
            try {
                String employeeJsonFormat = generateJsonResponseFromEmployee(optionalEmployee.get());
                return new ResponseEntity<Employee>(objectMapper.readValue(employeeJsonFormat, Employee.class), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updateEmployee(@ApiParam(value = "Employee object that needs update in system" ,required=true )  @Valid @RequestBody Employee body) {
        try {
            EmployeeCommand command = createEmployeeCommandFromBody(body);
            command.setUuid(UUID.fromString(body.getUuid()));
            employeeBizService.update(command);

        } catch (Exception e){
            log.error("Error on update employee : " ,e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private String generateJsonResponseFromEmployee(com.takeaway.challenge.model.Employee employee) throws JsonProcessingException {
        return objectMapper.writeValueAsString(employee);
    }

    private EmployeeCommand createEmployeeCommandFromBody(Employee body) {
        EmployeeCommand command = new EmployeeCommand();
        command.setMailAddress(body.getMailAddress() != null ? body.getMailAddress() : null);
        command.setFirstName(body.getFirstName() != null ? body.getFirstName() : null);
        command.setLastName(body.getLastName() != null ? body.getLastName() : null);
        command.setBirthday(body.getBirthday() != null ? LocalDate.parse(body.getBirthday()) : null);
        command.setDepartmentCommand(body.getDepartment() != null ? new DepartmentCommand(body.getDepartment().getName()) : null);
        return command;
    }

}
