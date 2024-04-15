package com.mikhail.timetracking.controller;

import com.mikhail.timetracking.service.TimeTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-tracking")
public class TimeTrackingController {

    private final TimeTrackingService timeTrackingService;

    @Autowired
    public TimeTrackingController(TimeTrackingService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getMethodExecutionStatistics() {
        try {
            Object stats = timeTrackingService.getMethodExecutionStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while retrieving execution statistics.");
        }
    }
}
