package com.takeaway.challenge.service;

import com.sun.jdi.request.InvalidRequestStateException;
import com.takeaway.challenge.command.EmployeeCommand;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeBizServiceTest {

    @Autowired
    private EmployeeBizService service;

    @MockBean
    private EmployeeDataService employeeDataService;

    @MockBean
    private Employee employee;

    @MockBean
    private Department department;

    @Test
    public void create_works_fine() {
        //given
        EmployeeCommand command = mockEmployeeCommand();
        Employee employee = mockEmployee();
        Mockito.when(employeeDataService.save(any(Employee.class))).thenReturn(employee);

        //when
        Employee result = service.create(command);

        //then:
        verify(employeeDataService, times(1)).save(any(Employee.class));

        //and
        assertNotNull(result);
        assertEmployee(result,command);
    }

    @Test(expected = InvalidRequestStateException.class)
    public void create_failed_mailAddress_is_null() {
        //given
        EmployeeCommand command = new EmployeeCommand();

        //when
        service.create(command);
    }

    @Test
    public void findByUuid_works_fine() {
        //given
        EmployeeCommand command = new EmployeeCommand();
        command.setUuid(UUID.randomUUID());
        Mockito.when(employeeDataService.save(any(Employee.class))).thenReturn(employee);

        //when
        Optional<Employee> result = service.findByUuid(command.getUuid());

        //then:
        verify(employeeDataService, times(1)).findByUuid(any(UUID.class));

        //and
        assertNotNull(result);
    }


    @Test(expected = InvalidRequestStateException.class)
    public void finByUuid_failed_uuid_is_null() {
        //given
        EmployeeCommand command = new EmployeeCommand();

        //when
        service.create(command);
    }

    @Test
    public void update_works_fine() {
        //given
        Employee employee = mockEmployee();
        EmployeeCommand command = new EmployeeCommand();
        command.setUuid(UUID.randomUUID());
        command.setFirstName("Elaheh");
        command.setMailAddress("elaheh.jamshidpey@gmail.com");
        Mockito.when(employeeDataService.findByUuid(any(UUID.class))).thenReturn(Optional.of(employee));
        Mockito.when(employeeDataService.save(any(Employee.class))).thenReturn(employee);

        //when
        Employee result = service.update(command);

        //then:
        verify(employeeDataService, times(1)).save(any(Employee.class));

        //and
        assertNotNull(result);
        assertEquals(result.getFirstName(),command.getFirstName());
        assertEquals(result.getMailAddress(),command.getMailAddress());
        assertEquals(result.getLastName(),employee.getLastName());
        assertEquals(result.getBirthday(),employee.getBirthday());
        assertEquals(result.getDepartment(),employee.getDepartment());
    }


    @Test(expected = InvalidRequestStateException.class)
    public void update_failed_employee_not_found() {
        //given
        EmployeeCommand command = mockEmployeeCommand();
        Mockito.when(employeeDataService.save(any(Employee.class))).thenReturn(null);

        //when
        service.update(command);
    }

    @Test(expected = InvalidRequestStateException.class)
    public void update_failed_uuid_is_null() {
        //given
        EmployeeCommand command = new EmployeeCommand();

        //when
        service.update(command);
    }

    @Test
    public void delete_works_fine() {
        //given
        EmployeeCommand command = new EmployeeCommand();
        command.setUuid(UUID.randomUUID());
        Mockito.when(employeeDataService.save(any(Employee.class))).thenReturn(employee);

        //when
        service.delete(command.getUuid());

        //then:
        verify(employeeDataService, times(1)).deleteByUuid(any(UUID.class));

    }

    @Test(expected = InvalidRequestStateException.class)
    public void delete_failed_uuid_is_null() {
        //given
        EmployeeCommand command = new EmployeeCommand();

        //when
        service.create(command);
    }

    private EmployeeCommand mockEmployeeCommand(){
        EmployeeCommand command = new EmployeeCommand();
        command.setUuid(UUID.randomUUID());
        command.setFirstName("Elham");
        command.setLastName("Jamshidpey");
        command.setMailAddress("elham.jamshidpey@gmail.com");
        command.setBirthday(LocalDate.of(1990,12,21));

        command.setDepartment(department);
        return command;
    }

    private Employee mockEmployee(){
        Employee employee = new Employee();
        employee.setFirstName("Elham");
        employee.setLastName("Jamshidpey");
        employee.setMailAddress("elham.jamshidpey@gmail.com");
        employee.setBirthday(LocalDate.of(1990,12,21));

        employee.setDepartment(department);
        return employee;

    }

    private void assertEmployee(Employee result, EmployeeCommand command) {
        assertEquals(result.getFirstName(),command.getFirstName());
        assertEquals(result.getLastName(),command.getLastName());
        assertEquals(result.getMailAddress(),command.getMailAddress());
        assertEquals(result.getBirthday(),command.getBirthday());
        assertEquals(result.getDepartment(),command.getDepartment());
    }
}
