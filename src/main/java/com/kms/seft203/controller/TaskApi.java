package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskApi {

    @Autowired
    private TaskService taskService;

    /**
     * @param id
     * @param email
     * @return
     * @throws TaskNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getByFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email) throws TaskNotFoundException, ContactNotFoundException {

        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        if (id != null) {
            log.info("Get one task by id: {} started...", + id);
            taskResponseDtoList = taskService.getById(id);
        }
        else if (email != null) {
            log.info("Get all tasks by email: {} started", email);
            taskResponseDtoList = taskService.getByUserEmail(email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createNewTask(@RequestBody @Valid TaskCreateDto taskCreateDto) throws ContactNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskCreateDto));
    }
}
