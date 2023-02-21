package com.alex.demo.repository;

import com.alex.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTaskName(String taskName);
    Task findTaskById(Long id);

    @Query(value = "select * from tasks where status in ('IN_PROGRESS', 'WAITING_FOR_APPROVE') and user_id = :userId", nativeQuery = true)
    List<Task> findAllInProgressAndWaitingForApproveTasks(@Param("userId") Long userId);

    @Query(value = "select * from tasks where status = 'COMPLETED' and user_id = :userId", nativeQuery = true)
    List<Task> findAllCompletedTasks(@Param("userId") Long userId);

    @Query(value = "select * from tasks where status = 'EXPIRED' and user_id = :userId", nativeQuery = true)
    List<Task> findAllExpiredTasks(@Param("userId") Long userId);

    void deleteTaskById(Long id);
}
