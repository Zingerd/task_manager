package com.example.task_manager.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    public Long id;
    public String taskName;
    public Status status;
    public String description;
}
