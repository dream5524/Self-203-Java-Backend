package com.kms.seft203.service;

import com.kms.seft203.dto.TaskCreateDto;
import com.kms.seft203.dto.TaskResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Task;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * This function return a list of taskDto from database which belong to a particular contact.
     * param: email
     * return: a list of TaskResponseDto
     */
    @Override
    public List<TaskResponseDto> getByUserEmail(String email) {
        List<Task> tasks = taskRepository.findByUserEmail(email);
        return tasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> getById(Integer id) {
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskResponseDtoList.add(modelMapper.map(optionalTask.get(), TaskResponseDto.class));
        }
        return taskResponseDtoList;
    }

    /**
     * This function is implemented to create and save a new Task into database.
     * @param taskCreateDto
     * @return TaskResponseDto
     * @throws ContactNotFoundException
     */
    @Override
    public TaskResponseDto save(TaskCreateDto taskCreateDto) throws ContactNotFoundException {
        String email = taskCreateDto.getEmail();
        Optional<Contact> contactFromDb = contactRepository.findByEmail(email);
        if (contactFromDb.isEmpty()) {
            throw new ContactNotFoundException("Error occurs! Contact of email" + email + " not found");
        }
        Contact contact = contactFromDb.get();
        Task task = modelMapper.map(taskCreateDto, Task.class);
        task.setContact(contact);
        task.setDateCreated(LocalDate.now());
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskResponseDto.class);
    }
}
