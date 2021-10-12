package com.kms.seft203.service;

import com.kms.seft203.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> getByUserEmail(String email);
}
