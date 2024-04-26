package com.example.task_manager.service;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;

import java.sql.SQLException;
import java.util.List;

public interface TaskManagerService {

    String createTask(TaskDto taskDto) throws SQLException;

    List<TaskDto> getListTask();

    String removeTaskById(Long id);

    String updateStatus(ChangeStatus changeStatus);

    boolean updateTask(TaskDto taskDto, Long id);
}
