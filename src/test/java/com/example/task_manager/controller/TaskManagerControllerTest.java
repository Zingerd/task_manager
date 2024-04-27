package com.example.task_manager.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.task_manager.dto.Status;
import com.example.task_manager.dto.TaskDto;
import com.example.task_manager.entity.ChangeStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTaskValidTaskDtoReturnsOk() throws Exception {
        TaskDto validTaskDto = new TaskDto();
        validTaskDto.setId(1L);
        validTaskDto.setTaskName("Test Task");
        validTaskDto.setStatus(Status.NEW);
        validTaskDto.setDescription("dsdsds");


        ResultActions result = mockMvc.perform(post("/api/task_management")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validTaskDto)));

        result.andExpect(status().isOk())
                .andExpect(content().string("Create new task with id : 1"));
    }

    @Test
    public void testCreateTaskNullTaskDtoReturnsBadRequest() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/task_management")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTaskInvalidTaskDtoReturnsBadRequest() throws Exception {
        TaskDto invalidTaskDto = new TaskDto();

        ResultActions result = mockMvc.perform(post("/api/task_management")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTaskDto)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStatusNullChangeStatusReturnsBadRequest() throws Exception {
        ResultActions result = mockMvc.perform(put("/api/task_management/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStatusInvalidChangeStatusReturnsBadRequest() throws Exception {
        ChangeStatus invalidChangeStatus = new ChangeStatus();

        ResultActions result = mockMvc.perform(put("/api/task_management/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidChangeStatus)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetListTask_ReturnsList() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/task_management")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
