package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskApi {
    private static final Logger logger = LoggerFactory.getLogger(TaskApi.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/{email}")
    public ResponseEntity<List<TaskResponseDto>> getByUserEmail(@PathVariable String email) {
        logger.info("Get all tasks by email: " + email + " started...");
        List<TaskResponseDto> tasks = taskService.getByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable Integer id) throws ContactNotFoundException {
        logger.info("Get one task by id: " + id + " started...");
        TaskResponseDto taskResponseDto = taskService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
    }
}
