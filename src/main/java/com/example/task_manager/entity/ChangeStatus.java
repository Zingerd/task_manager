package com.example.task_manager.entity;

import com.example.task_manager.dto.Status;
import lombok.Data;

@Data
public class ChangeStatus {
    private Long id;
    private Status status;
}