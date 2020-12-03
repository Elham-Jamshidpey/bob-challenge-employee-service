package com.takeaway.challenge.service;

import com.sun.jdi.request.InvalidRequestStateException;
import com.takeaway.challenge.command.DepartmentCommand;
import com.takeaway.challenge.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentBizServiceTest {

    @Autowired
    private DepartmentBizService service;

    @MockBean
    private DepartmentDataService departmentDataService;

    @MockBean
    private Department department;

    @Test
    public void create_works_fine() {
        //given
        DepartmentCommand command = new DepartmentCommand("Engineering");
        Mockito.when(departmentDataService.create(any(Department.class))).thenReturn(department);

        //when
        Department result = service.create(command);

        //then:
        verify(departmentDataService, times(1)).create(any(Department.class));

        //and
        assertNotNull(result);
        assertEquals(result.getName(),department.getName());
    }

    @Test(expected = InvalidRequestStateException.class)
    public void create_failed_nameIsNull() {
        //given
        DepartmentCommand command = new DepartmentCommand(null);

        //when
        service.create(command);
    }

    @Test(expected = InvalidRequestStateException.class)
    public void create_failed_nameIsEmpty() {
        //given
        DepartmentCommand command = new DepartmentCommand("");

        //when
        service.create(command);
    }
}
