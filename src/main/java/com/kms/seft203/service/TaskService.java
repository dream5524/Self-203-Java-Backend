package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;

import java.util.List;

public interface TaskService {
    TaskResponseDto save(TaskCreateDto task) throws ContactNotFoundException;
    List<TaskResponseDto> getAllByFilter(Integer id, String email, String status);
}
