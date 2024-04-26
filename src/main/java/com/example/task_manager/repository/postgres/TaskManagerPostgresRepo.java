package com.example.task_manager.repository.postgres;

import com.example.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskManagerPostgresRepo extends JpaRepository<Task, Long> {

}
