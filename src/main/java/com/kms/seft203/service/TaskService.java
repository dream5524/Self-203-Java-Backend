package com.kms.seft203.service;

import com.kms.seft203.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getByUserEmail(String email);
}
