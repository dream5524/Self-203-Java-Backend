package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskApi {

    @Autowired
    private TaskService taskService;

    /**
     *
     * @param id
     * @param email
     * @param status
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getByFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status
    ) {
        List<TaskResponseDto> taskResponseDtoList = taskService.getAllByFilter(id, email, status);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createNewTask(@RequestBody @Valid TaskCreateDto taskCreateDto) throws ContactNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskCreateDto));
    }
}
