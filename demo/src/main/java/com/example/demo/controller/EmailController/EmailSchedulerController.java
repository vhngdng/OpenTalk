package com.example.demo.controller.EmailController;

import com.example.demo.Scheduler.ScheduleDto.Request;
import com.example.demo.Scheduler.ScheduleDto.Response;
import com.example.demo.service.EmailService.EmailScheduleService;
import com.example.demo.service.EmailService.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmailSchedulerController {
    @Autowired
    private JavaMailSender sender;

        @Autowired
    private EmailService emailService;
    @Autowired
    private EmailScheduleService scheduleService;


    @PostMapping("schedule/send-email")
    public ResponseEntity<Response> createSchedulerInviteOpenTalk(@RequestBody Request request) throws SchedulerException {
        String scheduleId = scheduleService.createMailSchedule(request);
        log.info("before response =====================================");
//
        Response response = scheduleService.getSchedule(Integer.parseInt(scheduleId));
        log.info("=====================================");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


}

