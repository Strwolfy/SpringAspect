package com.mikhail.timetracking.repository;


import com.mikhail.timetracking.model.ExecutionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository
        extends JpaRepository<ExecutionTime, Long> {
}
