package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskDto;
import com.kms.seft203.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class TaskControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void getTaskByUserEmailTest_whenSuccess_thenReturnStatusOk() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String description = "Edit database";
        Boolean isCompleted = false;
        LocalDate dateCreated = LocalDate.of(2021, Month.JANUARY, 22);
        List<TaskDto> mockTaskDtoList = new ArrayList<>();
        mockTaskDtoList.add(new TaskDto(email, description, isCompleted, dateCreated));

        Mockito.when(taskService.getByUserEmail(email)).thenReturn(mockTaskDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + email)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email").value(email))
                .andExpect(jsonPath("$.[0].description").value(description))
                .andExpect(jsonPath("$.[0].isCompleted").value(isCompleted))
                .andExpect(jsonPath("$.[0].dateCreated").value(dateCreated.toString()));
    }
}
