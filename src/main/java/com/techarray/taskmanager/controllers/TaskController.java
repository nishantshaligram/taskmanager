package com.techarray.taskmanager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.techarray.taskmanager.dto.CreateTaskDTO;
import com.techarray.taskmanager.dto.ErrorResponseDTO;
import com.techarray.taskmanager.entities.TaskEntity;
import com.techarray.taskmanager.services.TaskService;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable("id") Integer id) {
        var task = taskService.getTaskById( id );
        if( task == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( task );
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(task);
    }
    
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){
        if( e instanceof ParseException ) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date format"));
        }
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResponseDTO("Internal Server Error"));
    }
}
