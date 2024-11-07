package com.techarray.taskmanager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.techarray.taskmanager.dto.CreateTaskDTO;
import com.techarray.taskmanager.entities.TaskEntity;
import com.techarray.taskmanager.services.TaskService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController( TaskService taskService ) {
        this.taskService = taskService;
    }
    
    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        var tasks = taskService.getTasks();
        return ResponseEntity.ok( tasks );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(int id) {
        var task = taskService.getTaskById( id );
        if( task == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( task );
    }

    @PostMapping("path")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(task);
    }
    
    
}
