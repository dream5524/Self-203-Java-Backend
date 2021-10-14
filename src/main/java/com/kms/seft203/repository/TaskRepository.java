package com.kms.seft203.repository;

import com.kms.seft203.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select task from Task task where task.contact.user.email = ?1")
    List<Task> findByUserEmail(String email);
}
