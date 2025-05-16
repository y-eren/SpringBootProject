package com.tasktracker.tasktrackery.service;


import com.tasktracker.tasktrackery.exception.TaskNotFoundException;
import com.tasktracker.tasktrackery.model.Task;
import com.tasktracker.tasktrackery.repository.TaskRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {


    private final TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByTaskStatus(String status) {
        return taskRepository.findByStatus(status);
    }


    public Task updateTask(Long id, Task task) {
        return taskRepository.findById(id)
                .map(task1 -> {
                    task1.setTitle(task.getTitle());
                    task1.setDescription(task.getDescription());
                    task1.setStatus(task.getStatus());
                    return taskRepository.save(task1);
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));


    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task markAsDone(Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus("done");
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task markAsInProgress(Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus("in-progress");
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }




}
