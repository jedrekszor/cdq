package com.jszor.cdq.service;

import com.jszor.cdq.dto.GetTaskDataResponse;
import com.jszor.cdq.exception.NoTaskFoundException;
import com.jszor.cdq.persistence.TaskRepository;
import com.jszor.cdq.persistence.model.Task;
import com.jszor.cdq.util.PatternHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final PatternHelper patternHelper;

    @Override
    @Async
    public CompletableFuture<UUID> createTask(
            String input,
            String pattern
    ) throws InterruptedException {
        Task task = new Task(input, pattern);
        patternHelper.findPattern(task);
        return CompletableFuture.completedFuture(task.getId());
    }

    @Override
    @Async
    public CompletableFuture<List<Task>> getTasks() throws InterruptedException {
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(taskRepository.findAll());
    }

    @Override
    @Async
    public CompletableFuture<GetTaskDataResponse> getTaskData(UUID id) throws InterruptedException {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new NoTaskFoundException(String.format("No task with id %s", id))
        );
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(new GetTaskDataResponse(task.getPosition(), task.getTypos(), task.getStatus()));
    }

    @Transactional
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
