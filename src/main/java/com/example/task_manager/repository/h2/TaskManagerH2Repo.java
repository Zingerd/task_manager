package com.example.task_manager.repository.h2;

import com.example.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskManagerH2Repo extends JpaRepository<Task, Long> {

}
