package com.tasktracker.tasktrackery.controller;


import com.tasktracker.tasktrackery.model.Task;
import com.tasktracker.tasktrackery.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/saveTask")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping("/getTasks")
    public ResponseEntity<List<Task>> getAllDoneTasks(@RequestParam String status) {
        return ResponseEntity.ok(taskService.findByTaskStatus(status));
    }

    @GetMapping("/allTasks")
    public ResponseEntity<List<Task>> getAllTasks( ){
        return ResponseEntity.ok(taskService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping
    public void deleteTask(@PathVariable Long id) {
         taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/mark-done")
    public ResponseEntity<Task> markDone(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markAsDone(id));
    }

    @PatchMapping("/{id}/mark-in-progress")
    public ResponseEntity<Task> markInProgress(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markAsInProgress(id));
    }

}
