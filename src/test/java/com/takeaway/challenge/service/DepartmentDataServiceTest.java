package com.takeaway.challenge.service;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentDataServiceTest {

    @Autowired
    private DepartmentDataService service;

    @MockBean
    private DepartmentRepository repository;

    @Test
    public void create_works_fine() {
        //given
        Department department = new Department();
        department.setName("Engineering");
        Mockito.when(repository.save(department)).thenReturn(department);

        //when
        Department result = service.create(department);

        //then
        verify(repository, times(1)).save(department);

        //and
        assertNotNull(result);
        assertEquals(result.getName(),department.getName());
    }
}
