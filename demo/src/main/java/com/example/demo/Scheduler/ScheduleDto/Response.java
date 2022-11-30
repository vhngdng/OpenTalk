package com.example.demo.Scheduler.ScheduleDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class Response {
    private String host;
    
    private Integer scheduleId;

    private LocalDateTime scheduledTime;

    private ZoneId zoneId;

}
