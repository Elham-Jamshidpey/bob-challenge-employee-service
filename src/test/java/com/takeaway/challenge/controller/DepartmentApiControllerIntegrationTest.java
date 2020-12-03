package com.takeaway.challenge.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createDepartment_works_fine() throws Exception{
        this.mockMvc.perform(post("/v1/department").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"IT\"}")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createDepartment_failed_invalidCommand() throws Exception{
        this.mockMvc.perform(post("/v1/department").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\"}")).andDo(print()).andExpect(status().isBadRequest());
    }

}
