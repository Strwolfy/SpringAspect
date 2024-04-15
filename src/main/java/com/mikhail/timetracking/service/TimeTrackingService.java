package com.mikhail.timetracking.service;

import com.mikhail.timetracking.model.ExecutionTime;
import com.mikhail.timetracking.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeTrackingService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeTrackingService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Object getMethodExecutionStatistics() {
        List<ExecutionTime> executions = timeRepository.findAll();
        
        Map<String, Double> averageExecutionTimes = executions.stream()
            .collect(Collectors.groupingBy(ExecutionTime::getMethodName,
                    Collectors.averagingLong(ExecutionTime::getExecutionTime)));
                    
        Map<String, Long> countExecutions = executions.stream()
            .collect(Collectors.groupingBy(ExecutionTime::getMethodName, Collectors.counting()));
        
        return Map.of("averageExecutionTimes", averageExecutionTimes, "countExecutions", countExecutions);
    }

    @Async
    @Transactional
    public void saveExecutionTimeSync(String methodName, long executionTime, LocalDateTime executionDateTime) {
        ExecutionTime methodExecutionTime = new ExecutionTime();
        methodExecutionTime.setMethodName(methodName);
        methodExecutionTime.setExecutionTime(executionTime);
        methodExecutionTime.setExecutionDateTime(executionDateTime);
        timeRepository.save(methodExecutionTime);
    }

    @Transactional
    public void saveExecutionTime(String methodName, long executionTime, LocalDateTime executionDateTime) {
        ExecutionTime record = new ExecutionTime(methodName, executionTime, executionDateTime);
        timeRepository.save(record);
    }
}
