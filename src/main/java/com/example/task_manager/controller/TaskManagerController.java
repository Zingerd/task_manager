package com.example.task_manager.controller;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;
import com.example.task_manager.service.impl.TaskManagerServiceImpl;
import com.example.task_manager.tools.ValidatorHandler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/task_management")
public class TaskManagerController {

    private final TaskManagerServiceImpl taskManagerService;
    public TaskManagerController(TaskManagerServiceImpl taskManagerService) {
        this.taskManagerService = taskManagerService;
    }

    /**
     * Method create task.
     *
     * @param taskDto contain mandatory fields for create task
     * @return ResponseEntity<String> with the answer about creating a task
     * @throws NullPointerException when some of the required fields are missing
     */
    @Operation(
            summary = "create task")
    @PostMapping()
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto) {
        try {
            ValidatorHandler.validatorTaskDto(taskDto);
            return ResponseEntity.ok(taskManagerService.createTask(taskDto));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Method update status task.
     *
     * @param changeStatus contain mandatory fields for update status task
     * @return ResponseEntity<String> with the answer about update a task
     * @throws Exception when some of the required fields are missing
     */
    @Operation(
            summary = "update task")
    @PutMapping ({"/{id}"})
    public ResponseEntity<String> updateStatus(@RequestBody ChangeStatus changeStatus) {
        try {
            ValidatorHandler.validatorStatus(changeStatus);
            return ResponseEntity.ok(taskManagerService.updateStatus(changeStatus));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Method update body task.
     *
     * @param taskDto contain mandatory fields for update status task
     * @param id task id
     * @return ResponseEntity<String> with the answer about update a task
     */
    @Operation(
            summary = "update body task")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        boolean success = taskManagerService.updateTask(taskDto, id);
        if (success) {
            return ResponseEntity.ok("Fields updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update fields");
        }
    }

    /**
     * Method get all tasks.
     *
     * @return List<TaskDto>  get all tasks
     */
    @Operation(
            summary = "get all tasks")
    @GetMapping()
    public List<TaskDto> getListTask() {
        return taskManagerService.getListTask();
    }


    /**
     * Method remove task.
     *
     * @param id task id
     * @return ResponseEntity<String>  with the answer about removed a task
     */
    @Operation(
            summary = "remove task")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskManagerService.removeTaskById(id));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
