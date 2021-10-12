package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskDto;
import com.kms.seft203.service.TaskService;
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

    @Autowired
    private TaskService taskService;

    @GetMapping("/{email}")
    public ResponseEntity<List<TaskDto>> getByUserEmail(@PathVariable String email) {
        List<TaskDto> tasks = taskService.getByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
