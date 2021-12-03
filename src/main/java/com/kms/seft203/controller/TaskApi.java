package com.kms.seft203.controller;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.dto.TaskUpdateByIdDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.ServerUnknownException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskApi {

    @Autowired
    private TaskService taskService;

    /**
     * By providing the optional condition in the query param, this function is implemented
     * to return a list of Task when receives a get request from client.
     *
     * Sample request url: http://sample-api.com/tasks?email=duclocdk1999@gmail.com&page=10&size=20
     *
     * Query parameters explained:
     *      id: id of task in database
     *      email: email of the user who own this task
     *      status vs isCompleted: status=inactive / isCompleted=true, status=active / isCompleted=false
     *      page & size: size if the # of returned items per page, page p is the p_th page selected
     *                  from the returned items
     * @param id
     * @param email
     * @param status
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getByFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        List<TaskResponseDto> taskResponseDtoList = taskService.getAllByFilter(id, email, status, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDtoList);
    }

    /**
     * By providing a taskCreateDto, this function is implemented to save new Task to database
     * when receives a createNewTask request from the client-side.
     *
     * Sample request body:
     * {
     *     "description": "Design database",
     *     "isCompleted": false,
     *     "email": "duclocdk1999@gmail.com"
     * }
     *
     * @param taskCreateDto
     * @return
     * @throws ContactNotFoundException
     */
    @PostMapping
    public ResponseEntity<TaskResponseDto> createNewTask(@RequestBody @Valid TaskCreateDto taskCreateDto) throws ContactNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskCreateDto));
    }

    @PutMapping
    public ResponseEntity<TaskResponseDto> updateTaskById(@RequestBody @Valid TaskUpdateByIdDto taskUpdateByIdDto) throws TaskNotFoundException, ServerUnknownException {

        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateById(taskUpdateByIdDto));
    }

    @GetMapping("countBy")
    public ResponseEntity<Map<Object, Object>> countByStatus(){
        return ResponseEntity.ok().body(taskService.countByStatus());
    }
}
