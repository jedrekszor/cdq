package com.jszor.cdq.util;

import com.jszor.cdq.persistence.TaskRepository;
import com.jszor.cdq.persistence.model.Task;
import com.jszor.cdq.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatternHelperTest {

    @Autowired
    private PatternHelper patternHelper;

    @MockBean
    private TaskRepository taskRepository;

    private Task task;

    @Test
    public void testFindPatternA() throws InterruptedException {
        givenTaskWithInputAndPattern("ABCD", "BCD");
        whenFindPattern();
        thenExpectPositionAndTypos(1L, 0L);
    }

    @Test
    public void testFindPatternB() throws InterruptedException {
        givenTaskWithInputAndPattern("ABCD", "BWD");
        whenFindPattern();
        thenExpectPositionAndTypos(1L, 1L);
    }

    @Test
    public void testFindPatternC() throws InterruptedException {
        givenTaskWithInputAndPattern("ABCDEFG", "CFG");
        whenFindPattern();
        thenExpectPositionAndTypos(4L, 1L);
    }

    @Test
    public void testFindPatternD() throws InterruptedException {
        givenTaskWithInputAndPattern("ABCABC", "ABC");
        whenFindPattern();
        thenExpectPositionAndTypos(0L, 0L);
    }

    @Test
    public void testFindPatternE() throws InterruptedException {
        givenTaskWithInputAndPattern("ABCDEFG", "TDD");
        whenFindPattern();
        thenExpectPositionAndTypos(1L, 2L);
    }

    private void givenTaskWithInputAndPattern(String input, String pattern) {
        task = new Task();
        task.setInput(input);
        task.setPattern(pattern);
    }

    private void whenFindPattern() throws InterruptedException {
        patternHelper.findPattern(task);
    }

    private void thenExpectPositionAndTypos(Long position, Long typos) {
        assertThat(position).isEqualTo(task.getPosition());
        assertThat(typos).isEqualTo(task.getTypos());
    }
}
