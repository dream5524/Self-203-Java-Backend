package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;

import com.kms.seft203.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
class TaskControllerTest extends ControllerTest {

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
        List<TaskResponseDto> mockTaskResponseDtoList = new ArrayList<>();
        mockTaskResponseDtoList.add(new TaskResponseDto(description, isCompleted, dateCreated));

        Mockito.when(taskService.getByUserEmail(email)).thenReturn(mockTaskResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value(description))
                .andExpect(jsonPath("$.[0].isCompleted").value(isCompleted))
                .andExpect(jsonPath("$.[0].dateCreated").value(dateCreated.toString()));
    }

    @Test
    void getOneTaskByIdTest_WhenSuccess_ThenReturnStatusIsOk() throws Exception {
        Integer id = 10;
        TaskResponseDto taskResponseDto = new TaskResponseDto("Apply Logger", false);
        List<TaskResponseDto> mockTaskResponseDtoList = new ArrayList<>();
        mockTaskResponseDtoList.add(taskResponseDto);

        Mockito.when(taskService.getById(id)).thenReturn(mockTaskResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .param("id", String.valueOf(id))
                        .content(convertObjectToJsonString(taskResponseDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value(taskResponseDto.getDescription()))
                .andExpect(jsonPath("$.[0].isCompleted").value(taskResponseDto.getIsCompleted()));
    }

    @Test
    void getTaskByUserEmailTest_whenEmailNotFound_thenReturnErrorResponse() throws Exception {
        String email = "duclocdk1999@gmail.com";
        String description = "Edit database";
        Boolean isCompleted = false;
        LocalDate dateCreated = LocalDate.of(2021, Month.JANUARY, 22);
        String message = "Error occurs! Contact not found!";
        TaskCreateDto taskCreateDto = new TaskCreateDto(email, description, isCompleted);

        Mockito.when(taskService.save(taskCreateDto)).thenThrow(new ContactNotFoundException(message));

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                    .content(convertObjectToJsonString(taskCreateDto))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages[0]").value(message));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ab@ab.com,Fix bug,true",
            "duclocdk1999,,false",
            "duclocdk1999@gmail.com,Fix bug,",
            ",Fix bug,true",
            ",Fix bug,",
            ",,false",
            ",,"
    }, delimiter = ',')
    void testSave_whenInputAreInvalid_thenReturnStatusBadRequest(
            String email, String description, Boolean isCompleted) throws Exception {

        TaskCreateDto taskCreateDto = new TaskCreateDto(email, description, isCompleted);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .content(convertObjectToJsonString(taskCreateDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
