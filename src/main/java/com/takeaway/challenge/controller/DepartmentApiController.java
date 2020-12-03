package com.takeaway.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.command.DepartmentCommand;
import com.takeaway.challenge.service.DepartmentBizService;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-12-03T00:57:40.657Z")

@Controller
public class DepartmentApiController implements DepartmentApi {

    @Autowired
    private DepartmentBizService departmentBizService;

    private static final Logger log = LoggerFactory.getLogger(DepartmentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DepartmentApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addDepartment(@ApiParam(value = "Department object that needs to be added to the system" ,required=true )  @Valid @RequestBody Department body) {
        try {
            departmentBizService.create(new DepartmentCommand(body.getName()));
        } catch (Exception e){
            log.error("Error on Department creation : " ,e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
