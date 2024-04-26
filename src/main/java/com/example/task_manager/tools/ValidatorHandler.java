package com.example.task_manager.tools;




import com.example.task_manager.dto.Status;
import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.example.task_manager.dto.Status.COMPLEAT;
import static com.example.task_manager.dto.Status.PROCESSING;
import static com.example.task_manager.dto.Status.TESTING;
import static com.example.task_manager.dto.Status.NEW;


public class ValidatorHandler {


    private static final Set<String> STATUS_FLING = Set.of(NEW.name(), PROCESSING.name(), TESTING.name(), COMPLEAT.name());

    public static void validatorTaskDto(TaskDto taskDto) {
        Objects.requireNonNull(taskDto, "TaskDto must not be null");
        Objects.requireNonNull(taskDto.getTaskName(), "TaskName must not be null");
        Objects.requireNonNull(taskDto.getId(), "Task id must not be null");
        Objects.requireNonNull(taskDto.getStatus(), " Task status must not be null");
    }
    public static void validatorStatus(ChangeStatus changeStatus) {
                if (!STATUS_FLING.contains(changeStatus.getStatus().getStatus())) {
            throw new IllegalArgumentException("Invalid value for statusFlight, " +
                    "please use these status values [NEW, PROCESSING, TESTING, COMPLEAT]");
        }
    }

}
