package com.kms.seft203.repository;

import com.kms.seft203.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<Task, Integer>, PagingAndSortingRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    @Query(value = "SELECT task FROM Task as task " +
            "WHERE (:id is null or task.id=:id) " +
            "and (:email is null or task.contact.user.email like %:email% ) " +
            "and (:isCompleted is null or task.isCompleted is :isCompleted )")
    Page<Task> findAllByInputField(Integer id, String email, Boolean isCompleted, Pageable pageable);

    @Query("select t.isCompleted as completed, count(t) as count from Task t group by t.isCompleted")
    List<Map<Object, Object>> countByStatus();
}
