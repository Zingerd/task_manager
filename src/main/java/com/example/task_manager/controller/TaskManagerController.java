package com.example.task_manager.controller;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;
import com.example.task_manager.service.impl.TaskManagerServiceImpl;
import com.example.task_manager.tools.ValidatorHandler;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/task_management")
public class TaskManagerController {

    TaskManagerServiceImpl taskManagerService;
    TaskManagerController(TaskManagerServiceImpl taskManagerService) {
        this.taskManagerService = taskManagerService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto) {
        try {
            ValidatorHandler.validatorTaskDto(taskDto);
            return ResponseEntity.ok(taskManagerService.createTask(taskDto));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping ("/update-status/{id}")
    public ResponseEntity<String> updateStatus(@RequestBody ChangeStatus changeStatus) {
        try {
            ValidatorHandler.validatorStatus(changeStatus);
            return ResponseEntity.ok(taskManagerService.updateStatus(changeStatus));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/update-task/{id}")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        boolean success = taskManagerService.updateTask(taskDto, id);
        if (success) {
            return ResponseEntity.ok("Fields updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update fields");
        }
    }

    @GetMapping("/get")
    public List<TaskDto> getListTask() {
        return taskManagerService.getListTask();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String>  getListTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskManagerService.removeTaskById(id));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
