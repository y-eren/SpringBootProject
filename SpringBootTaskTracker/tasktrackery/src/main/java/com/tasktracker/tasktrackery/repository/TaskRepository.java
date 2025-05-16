package com.tasktracker.tasktrackery.repository;

import com.tasktracker.tasktrackery.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);
}


