package com.kms.seft203.repository;

import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    Optional<Task> findById(Integer id);
    Optional<Contact> findByContact(Contact contact);

}
