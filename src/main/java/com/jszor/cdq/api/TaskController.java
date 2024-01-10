package com.jszor.cdq.api;

import com.jszor.cdq.dto.CreateTaskRequest;
import com.jszor.cdq.dto.GetTaskDataResponse;
import com.jszor.cdq.persistence.model.Task;
import com.jszor.cdq.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping()
    public ResponseEntity<UUID> createTask(
            @RequestBody CreateTaskRequest request
            ) throws ExecutionException, InterruptedException {
        CompletableFuture<UUID> futureResult = taskService.createTask(request.getInput(), request.getPattern());
        CompletableFuture.allOf(futureResult).join();
        return ResponseEntity.ok(futureResult.get());
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getTasks() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Task>> futureResult = taskService.getTasks();
        CompletableFuture.allOf(futureResult).join();
        return ResponseEntity.ok(futureResult.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskDataResponse> getTaskData(
            @PathVariable UUID id
    ) throws ExecutionException, InterruptedException {
        CompletableFuture<GetTaskDataResponse> futureResult = taskService.getTaskData(id);
        CompletableFuture.allOf(futureResult).join();
        return ResponseEntity.ok(futureResult.get());
    }
}
