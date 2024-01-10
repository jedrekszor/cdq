package com.jszor.cdq.service;

import com.jszor.cdq.dto.GetTaskDataResponse;
import com.jszor.cdq.persistence.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public interface TaskService {

    CompletableFuture<UUID> createTask(String input, String pattern) throws InterruptedException;

    CompletableFuture<List<Task>> getTasks() throws InterruptedException;

    CompletableFuture<GetTaskDataResponse> getTaskData(UUID id) throws InterruptedException;

    void saveTask(Task task);
}
