package com.jszor.cdq.util;

import com.jszor.cdq.persistence.TaskRepository;
import com.jszor.cdq.persistence.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatternHelper {

    private final TaskRepository taskRepository;

    public void findPattern(
            Task task
    ) throws InterruptedException {
        char[] input = task.getInput().toCharArray();
        char[] pattern = task.getPattern().toCharArray();
        long result = 0;
        long position = 0;
        long typos = 0;
        for (int i = 0; i < input.length; i++) {
            Thread.sleep(2000);
            List<Boolean> patternComparator = new ArrayList<>();
            for (int p = 0; p < pattern.length; p++) {
                if (i + p < input.length) {
                    patternComparator.add(input[i + p] == pattern[p]);
                }
            }
            long localResult = patternComparator.stream().filter(x -> x).count();
            if (localResult > result) {
                result = localResult;
                position = i;
                typos = pattern.length - result;
            }
            task.setStatus(((i * 100L) / input.length));
            saveResults(task);
        }
        task.setStatus(100L);
        task.setPosition(position);
        task.setTypos(typos);
        saveResults(task);
    }

    @Transactional
    private void saveResults(Task task) {
        taskRepository.save(task);
    }
}
