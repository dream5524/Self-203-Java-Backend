package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskApi {
    private static final Logger logger = LoggerFactory.getLogger(TaskApi.class);

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getByFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email) throws TaskNotFoundException {

        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        if (id != null) {
            logger.info("Get one task by id: " + id + " started...");
            taskResponseDtoList = taskService.getById(id);
        }
        else if (email != null) {
            logger.info("Get all tasks by email: " + email + " started...");
            taskResponseDtoList = taskService.getByUserEmail(email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtoList);
    }
}
