package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import org.junit.Before;
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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeDataServiceTest {

    @Autowired
    private EmployeeDataService service;

    @MockBean
    private EmployeeRepository repository;

    @MockBean
    private Department department;

    private Employee employee;

    @Before
    public void setup(){
        employee = mockEmployee();
    }

    @Test
    public void save_works_fine() {
        //given
        Mockito.when(repository.save(employee)).thenReturn(employee);

        //when
        Employee result = service.save(employee);

        //then
        verify(repository, times(1)).save(employee);

        //and
        assertNotNull(result);
        assertEmployee(result,employee);
    }

    @Test
    public void findByUuid_works_fine() {
        //given
        Optional<Employee> optionalEmployee = Optional.of(employee);
        Mockito.when(repository.findById(employee.getUuid())).thenReturn(optionalEmployee);

        //when
        Optional<Employee> result = service.findByUuid(employee.getUuid());

        //then
        verify(repository, times(1)).findById(employee.getUuid());

        //and
        assertNotNull(result);
        assertEmployee(result.get(),employee);
    }

    @Test
    public void findByUuid_returns_null_employee_not_found_for_this_uuid() {
        //given
        UUID uuid = UUID.randomUUID();
        Mockito.when(repository.findById(uuid)).thenReturn(null);

        //when
        Optional<Employee> result = service.findByUuid(uuid);

        //then
        verify(repository, times(1)).findById(uuid);

        //and
        assertNull(result);
    }

    @Test
    public void deleteByUuid_works_fine() {
        //when
        service.deleteByUuid(employee.getUuid());

        //then
        verify(repository, times(1)).deleteById(employee.getUuid());
    }

    private void assertEmployee(Employee result, Employee employee) {
        assertEquals(result.getFirstName(),employee.getFirstName());
        assertEquals(result.getLastName(),employee.getLastName());
        assertEquals(result.getMailAddress(),employee.getMailAddress());
        assertEquals(result.getBirthday(),employee.getBirthday());
        assertEquals(result.getDepartment(),employee.getDepartment());
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
}
