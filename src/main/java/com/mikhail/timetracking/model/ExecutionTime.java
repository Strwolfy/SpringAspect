package com.mikhail.timetracking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ExecutionTime")
@NoArgsConstructor
public class ExecutionTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "execution_time")
    private long executionTime;

    @Column(name = "execution_date_time")
    private LocalDateTime executionDateTime;

    public ExecutionTime(String methodName, long executionTime, LocalDateTime executionDateTime) {
        this.methodName = methodName;
        this.executionTime = executionTime;
        this.executionDateTime = executionDateTime;
    }

}
