package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.dto.TaskUpdateByIdDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.ServerUnknownException;
import com.kms.seft203.exception.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    TaskResponseDto save(TaskCreateDto taskCreateDto) throws ContactNotFoundException;
    List<TaskResponseDto> getAllByFilter(Integer id, String email, String status, Integer page, Integer size);
    TaskResponseDto updateById(TaskUpdateByIdDto taskUpdateByIdDto) throws TaskNotFoundException, ServerUnknownException;
}
