package com.kms.seft203.service;

import com.kms.seft203.dto.TaskDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Task;
import com.kms.seft203.entity.User;
import com.kms.seft203.repository.TaskRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void getByUserEmailTest_whenSuccess_thenReturnTaskDtoList() {
        String email = "duclocdk1999@gmail.com";
        String description = "fix bug";
        Boolean isCompleted = true;
        LocalDate dateCreated = LocalDate.of(2021, Month.OCTOBER, 10);
        String firstName = "Loc";
        String lastName = "Nguyen";
        String password = "Abc123456789";
        String title = "Developer";
        String project = "Dashboard-seft203";
        User mockUser = new User(null, email, password, firstName + " " + lastName);

        Contact mockContact = new Contact(firstName, lastName, mockUser, title, project);
        List<Task> mockContactList = new ArrayList<>();
        mockContactList.add(new Task(null, description, isCompleted, mockContact, dateCreated));

        List<TaskDto> expectedResults = new ArrayList<>();
        expectedResults.add(new TaskDto(email, description, isCompleted, dateCreated));

        Mockito.when(taskRepository.findByUserEmail(email)).thenReturn(mockContactList);

        List<TaskDto> actualResults = taskService.getByUserEmail(email);

        assertEquals(1, actualResults.size());
        assertEquals(expectedResults.get(0).getEmail(), actualResults.get(0).getEmail());
        assertEquals(expectedResults.get(0).getDescription(), actualResults.get(0).getDescription());
        assertEquals(expectedResults.get(0).getIsCompleted(), actualResults.get(0).getIsCompleted());
        assertEquals(expectedResults.get(0).getDateCreated(), actualResults.get(0).getDateCreated());
    }
}
