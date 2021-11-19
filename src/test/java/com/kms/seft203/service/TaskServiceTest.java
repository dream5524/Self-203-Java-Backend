package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.dto.TaskUpdateByIdDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Task;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.TaskRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ContactRepository contactRepository;

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
        TaskCreateDto taskCreateDto = new TaskCreateDto(description, isCompleted, email);

        Mockito.when(contactRepository.findByEmail(email)).thenReturn(Optional.of(contact));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(mockTask);

        TaskResponseDto actualTaskResponseDto = taskService.save(taskCreateDto);

        Assert.assertEquals(description, actualTaskResponseDto.getDescription());
        Assert.assertEquals(isCompleted, actualTaskResponseDto.getIsCompleted());
        Assert.assertEquals(dateCreated, actualTaskResponseDto.getDateCreated());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,duclocdk1999@gmail.com,active",
            ",duclocdk1999@gmail.com,inactive",
            "1,,",
            ",,active",
            ",duclocdk1999@gmail.com,",
            ",,"
    }, delimiter = ',')
    void getByFilterTest_whenSuccess_thenReturnTaskResponseDtoList(Integer id, String email, String status) {
        String description = "writing unint testing for task api";
        LocalDate dateCreated = LocalDate.now();
        Boolean isCompleted = null;
        if (status != null) {
            isCompleted = true;
            if (status.equalsIgnoreCase("active")) {
                isCompleted = false;
            }
        }
        Page<Task> mockTasks = new PageImpl<Task>(Arrays.asList(
                new Task(id, description, isCompleted, null, dateCreated)
        ));
        Mockito.when(taskRepository.findAllByInputField(
                Mockito.eq(id),
                Mockito.eq(email),
                Mockito.eq(isCompleted),
                Mockito.any(Pageable.class))).thenReturn(mockTasks);

        List<TaskResponseDto> actualTaskResponseDtoList = taskService.getAllByFilter(id, email, status, null, null);

        Assert.assertEquals(description, actualTaskResponseDtoList.get(0).getDescription());
        Assert.assertEquals(dateCreated, actualTaskResponseDtoList.get(0).getDateCreated());
        Assert.assertEquals(isCompleted, actualTaskResponseDtoList.get(0).getIsCompleted());
    }

    @Test
    void testUpdateTaskById_whenSuccess_thenReturnTaskResponseDto() throws Exception {
        String description = "Writing unit testing for task-api";
        Integer id = 10;
        Boolean isCompleted = false;
        LocalDate dateCreated = LocalDate.of(1999, 02, 17);
        TaskUpdateByIdDto taskUpdateByIdDto = new TaskUpdateByIdDto(description, isCompleted, id);
        Task task = new Task(id, "hello world", true, null, dateCreated);
        Task mockUpdatedTask = new Task(id, description, isCompleted, null, dateCreated);


        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(mockUpdatedTask)).thenReturn(mockUpdatedTask);

        TaskResponseDto actualTaskResponseDto = taskService.updateById(taskUpdateByIdDto);

        Assert.assertEquals(description, actualTaskResponseDto.getDescription());
        Assert.assertEquals(id, actualTaskResponseDto.getId());
        Assert.assertEquals(isCompleted, actualTaskResponseDto.getIsCompleted());
        Assert.assertEquals(dateCreated, actualTaskResponseDto.getDateCreated());
    }

    @Test
    void testUpdateTaskById_whenNotFoundFromDb_thenReturnTaskNotFoundException() {
        String description = "description from updated request";
        Integer id = 10;
        Boolean isCompleted = false;
        TaskUpdateByIdDto taskUpdateByIdDto = new TaskUpdateByIdDto(description, isCompleted, id);

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = Assert.assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateById(taskUpdateByIdDto);
        });
        String message = "Error occurs! No task found for id " + id;
        Assert.assertEquals(message, exception.getMessage());
    }

    @Test
    void countByStatusTest_WhenSuccess_ThenReturnStatusOk(){
        Map<Object, Object> objectMap = new HashMap<>();
        objectMap.put("completed", false);
        objectMap.put("count", 5);

        List<Map<Object, Object>> objectList = Arrays.asList(objectMap);
        Mockito.when(taskRepository.countByStatus()).thenReturn(objectList);

        Map<Object, Object> actualObjectMap = taskService.countByStatus();

        Assert.assertNotNull(actualObjectMap);
        Assert.assertEquals(5, actualObjectMap.get(false));
    }
}
