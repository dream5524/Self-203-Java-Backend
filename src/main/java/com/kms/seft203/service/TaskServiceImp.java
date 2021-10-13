package com.kms.seft203.service;

import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.entity.Task;
import com.kms.seft203.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * This function return a list of taskDto from database which belong to a particular contact.
     * @param email
     * @return
     */
    @Override
    public List<TaskResponseDto> getByUserEmail(String email) {
        List<Task> tasks = taskRepository.findByUserEmail(email);
        return tasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }
}
