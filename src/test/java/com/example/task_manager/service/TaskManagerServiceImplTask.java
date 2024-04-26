package com.example.task_manager.service;

import com.example.task_manager.dto.Status;
import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;
import com.example.task_manager.entity.Task;
import com.example.task_manager.repository.h2.TaskManagerH2Repo;
import com.example.task_manager.service.impl.TaskManagerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskManagerServiceImplTask {

    @InjectMocks
    TaskManagerServiceImpl taskManagerService;

    @Mock
    TaskManagerH2Repo taskManagerH2Repo;

    @Mock
    ModelMapper modelMapper;

    @Mock
    DataSource dataSource;

    @Test
    void createTask() throws SQLException {
        Mockito.when(dataSource.getConnection()).thenReturn(Mockito.mock(Connection.class));
        Mockito.when(dataSource.getConnection().isValid(100)).thenReturn(true);
        Assertions.assertNotNull(taskManagerService.createTask(buildTaskDto()));

    }

    @Test
    void getListTask() {
        Assertions.assertNotNull(taskManagerService.getListTask());

    }

    @Test
    void removeTaskById() {
        when(taskManagerH2Repo.findById(1L)).thenReturn(Optional.of(new Task()));
        Assertions.assertNotNull(taskManagerService.removeTaskById(1L));
    }

    @Test
    void updateStatus() {
        ChangeStatus changeStatus = new ChangeStatus();
        changeStatus.setStatus(Status.COMPLEAT);
        changeStatus.setId(1L);
        when(taskManagerH2Repo.findById(1L)).thenReturn(Optional.of(new Task()));
        Assertions.assertNotNull(taskManagerService.updateStatus(changeStatus));
    }

    @Test
    void updateTask() {

        when(taskManagerH2Repo.findById(1L)).thenReturn(Optional.of(new Task()));
        taskManagerService.updateTask(buildTaskDto(), 1L);
    }
    private TaskDto buildTaskDto() {
        return new TaskDto(1L, "tt", Status.NEW, "dsdsd");
    }
}
