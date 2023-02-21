package com.alex.demo.service;

import com.alex.demo.model.EnumStatus;
import com.alex.demo.model.Task;
import com.alex.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskService{

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findTaskById(Long id){
        return taskRepository.findTaskById(id);
    }

    public boolean setTaskCompleted(Long taskId) {
        taskRepository.findTaskById(taskId).setStatus(EnumStatus.WAITING_FOR_APPROVE);
        return true;
    }
    public boolean approveTask(Long taskId){
        taskRepository.findTaskById(taskId).setStatus(EnumStatus.COMPLETED);
        taskRepository.findTaskById(taskId).setCompleted(LocalDate.now());
        return true;
    }

    public boolean rejectTask(Long taskId){
        taskRepository.findTaskById(taskId).setStatus(EnumStatus.IN_PROGRESS);
        return true;
    }

    public boolean deleteTaskById(Long taskId){
        taskRepository.deleteTaskById(taskId);
        return true;
    }

    public boolean updateTask(Task task) {

        task.setStatus(EnumStatus.IN_PROGRESS);
        taskRepository.save(task);
        return true;
    }

    public List<Task> findAllInProgressAndWaitingForApproveTasks(Long userId){
        return taskRepository.findAllInProgressAndWaitingForApproveTasks(userId);
    }

    public List<Task> findAllCompletedTasks(Long userId){
        return taskRepository.findAllCompletedTasks(userId);
    }

    public List<Task> findAllExpiredTasks(Long userId){
        return taskRepository.findAllExpiredTasks(userId);
    }


}
