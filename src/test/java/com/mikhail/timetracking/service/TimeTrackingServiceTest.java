package com.mikhail.timetracking.service;


import com.mikhail.timetracking.model.ExecutionTime;
import com.mikhail.timetracking.repository.TimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;


@DataJpaTest
@Import(TimeTrackingService.class)
public class TimeTrackingServiceTest {

    @Autowired
    private TimeTrackingService timeTrackingService;

    @Autowired
    private TimeRepository repository;

    @BeforeEach
    void setUp() {
        // Очистка репозитория перед каждым тестом
        repository.deleteAll();

        // Добавление тестовых данных
        repository.save(new ExecutionTime("method1", 100, LocalDateTime.now()));
        repository.save(new ExecutionTime("method1", 200, LocalDateTime.now()));
        repository.save(new ExecutionTime("method2", 300, LocalDateTime.now()));
    }

    @Test
    public void testSaveExecutionTime() {
        String methodName = "method1";
        long executionTime = 100;
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        timeTrackingService.saveExecutionTimeSync(methodName, executionTime, now);

        ExecutionTime savedRecord = repository.findAll().get(0);
        assertThat(savedRecord.getMethodName()).isEqualTo(methodName);
        assertThat(savedRecord.getExecutionTime()).isEqualTo(executionTime);
        assertThat(now).isCloseTo(savedRecord.getExecutionDateTime(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void testGetMethodExecutionStatistics_EmptyRepository() {
        when(repository.findAll()).thenReturn(List.of());

        Map<String, Map<String, Object>> stats;
        stats = (Map<String, Map<String, Object>>) timeTrackingService.getMethodExecutionStatistics();

        assertEquals(0, stats.get("averageExecutionTimes").size());
        assertEquals(0, stats.get("countExecutions").size());
    }

}