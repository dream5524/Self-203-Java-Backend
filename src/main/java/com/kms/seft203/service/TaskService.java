package com.kms.seft203.service;

import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskResponseDto> getByUserEmail(String email);
    TaskResponseDto getById(Integer id) throws ContactNotFoundException;
}
