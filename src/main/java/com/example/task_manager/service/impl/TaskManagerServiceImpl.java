package com.example.task_manager.service.impl;

import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;
import com.example.task_manager.entity.Task;
import com.example.task_manager.repository.h2.TaskManagerH2Repo;
import com.example.task_manager.repository.postgres.TaskManagerPostgresRepo;
import com.example.task_manager.service.TaskManagerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Log4j2
public class TaskManagerServiceImpl implements TaskManagerService {

    private final TaskManagerH2Repo taskManagerH2Repo;

    private final TaskManagerPostgresRepo taskManagerPostgresRepo;

    private final ModelMapper modelMapper;

    private final DataSource h2DataSource;


    TaskManagerServiceImpl(TaskManagerH2Repo taskManagerH2Repo, ModelMapper modelMapper,
                           TaskManagerPostgresRepo taskManagerPostgresRepo,
                           @Qualifier("H2DataSource") DataSource h2DataSource) {
        this.taskManagerH2Repo = taskManagerH2Repo;
        this.modelMapper = modelMapper;
        this.taskManagerPostgresRepo = taskManagerPostgresRepo;
        this.h2DataSource = h2DataSource;


    }

    @Override
    public String createTask(TaskDto taskDto) {
        log.info("create Task");
        try {
            if (h2DataSource.getConnection().isValid(100)) {
                h2DataSource.getConnection().close();
                taskManagerH2Repo.save(convertTaskDtoToTaskEntity(taskDto));
                return String.format("Create new task with id : %s", taskDto.id);
            }
            throw new SQLException();
        } catch (SQLException e) {
            log.error("SQL Error saving to H2 database, switching to PostgreSQL", e);
                taskManagerPostgresRepo.save(convertTaskDtoToTaskEntity(taskDto));
                return String.format("Create new task with id : %s using PostgreSQL", taskDto.id);
        }
    }
    @Override
    public List<TaskDto> getListTask() {
        log.info("get List task ");
        try {
            if (h2DataSource.getConnection().isValid(100)) {
                h2DataSource.getConnection().close();
                return convertListTaskEntityToListTaskDto(taskManagerH2Repo.findAll());
            }
            throw new SQLException();
        } catch (SQLException e) {
            log.error("SQL Error get list to H2 database, switching to PostgresSQL", e);
            return convertListTaskEntityToListTaskDto(taskManagerPostgresRepo.findAll());
        }
    }

    @Override
    public String removeTaskById(Long id) {
        log.info("remove Task");
        try {
            if (h2DataSource.getConnection().isValid(100)) {
                h2DataSource.getConnection().close();
                taskManagerH2Repo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

                taskManagerH2Repo.deleteById(id);
                return String.format("Task, with this id: %s, removed", id);
            }
            throw new SQLException();
        } catch (SQLException e) {
            log.error("SQL Error remove to H2 database, switching to PostgresSQL", e);
                taskManagerPostgresRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
                taskManagerPostgresRepo.deleteById(id);
                return String.format("Task, with this id: %s, removed", id);
        }
    }

    @Override
    public String updateStatus(ChangeStatus changeStatus) {
        log.info("update status task");
        try {
            if (h2DataSource.getConnection().isValid(100)) {
                h2DataSource.getConnection().close();
                Task task = taskManagerH2Repo.findById(changeStatus.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
                task.setStatus(changeStatus.getStatus());
                taskManagerH2Repo.save(task);

                return String.format("Task, with this id: %s, removed", id);
            }
            throw new SQLException();
        } catch (SQLException e) {
            log.error("SQL Error remove to H2 database, switching to PostgresSQL", e);
            Task task = taskManagerH2Repo.findById(changeStatus.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));;
            task.setStatus(changeStatus.getStatus());
            taskManagerH2Repo.save(task);
            return String.format("Update is done with id %s", id);
        }
    }

    @Override
    public boolean updateTask(TaskDto taskDto, Long id) {
        log.info("update task");
        try {
            if (h2DataSource.getConnection().isValid(100)) {
                h2DataSource.getConnection().close();
                Task optionalTask = taskManagerH2Repo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
                Task task = getTask(taskDto, optionalTask);
                if (task == null) return false;
                taskManagerH2Repo.save(task);
                return true;
            }
            throw new SQLException();
        } catch (SQLException e) {
            log.error("SQL Error update task to H2 database, switching to PostgresSQL", e);
            Task task = taskManagerPostgresRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
            Task taskRs = getTask(taskDto, task);
            taskManagerPostgresRepo.save(taskRs);
            return true;
        }
    }

    private static Task getTask(TaskDto taskDto, Task task) {

        if (taskDto.getTaskName() != null) {
            task.setTaskName(taskDto.getTaskName());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        return task;
    }


    private Task convertTaskDtoToTaskEntity(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    private List<TaskDto> convertListTaskEntityToListTaskDto(List<Task> task) {
        return task.stream()
                .map(task1 -> modelMapper.map(task1, TaskDto.class))
                .collect(Collectors.toList());
    }

}
