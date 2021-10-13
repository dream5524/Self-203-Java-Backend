package com.kms.seft203.controller;

<<<<<<< HEAD

import com.kms.seft203.dto.SaveTaskRequest;
import com.kms.seft203.entity.Task;
import org.springframework.web.bind.annotation.*;
=======
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> e75ae5e9b36d8f30804424b3717cb1e24c9a1efd

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskApi {
<<<<<<< HEAD
    private static final Map<String, Task> DATA = new HashMap<>();

    @GetMapping
    public List<Task> getAll() {
        return new ArrayList<>(DATA.values());
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable String id) {
        return DATA.get(id);
    }

    @PostMapping
    public Task create(@RequestBody SaveTaskRequest request) {
        //DATA.put(request.getId(), request);
        return request;
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id, @RequestBody SaveTaskRequest request) {
        DATA.put(id, request);
        return request;
    }

    @DeleteMapping("/{id}")
    public Task delete(@PathVariable String id) {
        return DATA.remove(id);
=======

    @Autowired
    private TaskService taskService;

    @GetMapping("/{email}")
    public ResponseEntity<List<TaskResponseDto>> getByUserEmail(@PathVariable String email) {
        List<TaskResponseDto> tasks = taskService.getByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
>>>>>>> e75ae5e9b36d8f30804424b3717cb1e24c9a1efd
    }

}
