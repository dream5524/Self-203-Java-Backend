package com.kms.seft203.service;

import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getByUserEmail(String email);
    TaskResponseDto getById(Integer id) throws TaskNotFoundException;
}
