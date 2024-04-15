package com.mikhail.timetracking.service;

import com.mikhail.timetracking.model.ExecutionTime;
import com.mikhail.timetracking.repository.TimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest
@ActiveProfiles("test")
public class TimeTrackingServiceIntegrationTest {

    @Autowired
    private TimeTrackingService service;

    @Autowired
    private TimeRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void testSaveExecutionTime() {
        String methodName = "integrationTest";
        long executionTime = 500;
        LocalDateTime now = LocalDateTime.now();

        service.saveExecutionTime(methodName, executionTime, now);

        ExecutionTime result = repository.findAll().get(0);
        assertThat(result.getMethodName()).isEqualTo(methodName);
        assertThat(result.getExecutionTime()).isEqualTo(executionTime);
        assertThat(result.getExecutionDateTime()).isCloseTo(now, within(1, ChronoUnit.SECONDS));
    }
}
