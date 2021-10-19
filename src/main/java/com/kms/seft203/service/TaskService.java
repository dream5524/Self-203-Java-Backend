package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;

import java.util.List;

public interface TaskService {
    TaskResponseDto save(TaskCreateDto task) throws ContactNotFoundException;
    List<TaskResponseDto> getByUserEmail(String email);
    List<TaskResponseDto> getById(Integer id);
    List<TaskResponseDto> getByStatus(String status);
}
