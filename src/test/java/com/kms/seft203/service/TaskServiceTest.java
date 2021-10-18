package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Task;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.TaskRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    void getByUserEmailTest_whenSuccess_thenReturnTaskDtoList() throws ContactNotFoundException {
        String email = "duclocdk1999@gmail.com";
        String description = "fix bug";
        Boolean isCompleted = true;
        String firstName = "Loc";
        String lastName = "Nguyen";
        String password = "Abc123456789";
        String title = "Developer";
        String project = "Dashboard-seft203";
        LocalDate dateCreated = LocalDate.of(2021, 10, 10);
        User user = new User(null, email, password, firstName + " " + lastName);
        Contact contact = new Contact(firstName, lastName, user, title, project);
        Task task = new Task(1, description, isCompleted, contact, dateCreated);
        List<Task> mockContactList = new ArrayList<>();
        mockContactList.add(task);

        Mockito.when(contactRepository.findByEmail(email)).thenReturn(java.util.Optional.of(contact));
        Mockito.when(taskRepository.findByUserEmail(email)).thenReturn(mockContactList);

        List<TaskResponseDto> actualResults = taskService.getByUserEmail(email);

        assertEquals(1, actualResults.size());
        assertEquals(description, actualResults.get(0).getDescription());
        assertEquals(isCompleted, actualResults.get(0).getIsCompleted());
        assertEquals(dateCreated, actualResults.get(0).getDateCreated());
    }

    @Test
    void getTaskByIdTest_whenSuccess_thenReturnTaskDto() throws TaskNotFoundException {
        Integer id = 10;
        Task expectedTask = new Task("Apply logger", false);

        Mockito.when(taskRepository.findById(id)).thenReturn(java.util.Optional.of(expectedTask));

        List<TaskResponseDto> actualTaskResponseDto = taskService.getById(id);

        Assert.assertEquals(1, actualTaskResponseDto.size());
        Assert.assertEquals(expectedTask.getDescription(), actualTaskResponseDto.get(0).getDescription());
        Assert.assertEquals(expectedTask.getIsCompleted(), actualTaskResponseDto.get(0).getIsCompleted());
    }

    @Test
    void saveTest_whenSuccess_thenReturnTaskResponseDto() throws ContactNotFoundException {
        String email = "duclocdk1999@gmail.com";
        String description = "fix bug";
        Boolean isCompleted = true;
        String firstName = "Loc";
        String lastName = "Nguyen";
        String password = "Abc123456789";
        String title = "Developer";
        String project = "Dashboard-seft203";
        LocalDate dateCreated = LocalDate.of(2021, 10, 10);
        User user = new User(null, email, password, firstName + " " + lastName);
        Contact contact = new Contact(firstName, lastName, user, title, project);
        Task mockTask = new Task(1, description, isCompleted, contact, dateCreated);
        TaskCreateDto taskCreateDto = new TaskCreateDto(email, description, isCompleted);

        Mockito.when(contactRepository.findByEmail(email)).thenReturn(Optional.of(contact));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(mockTask);

        TaskResponseDto actualTaskResponseDto = taskService.save(taskCreateDto);

        Assert.assertEquals(description, actualTaskResponseDto.getDescription());
        Assert.assertEquals(isCompleted, actualTaskResponseDto.getIsCompleted());
        Assert.assertEquals(dateCreated, actualTaskResponseDto.getDateCreated());
    }
}
