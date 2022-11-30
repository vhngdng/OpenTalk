package com.example.demo.service.EmailService;

import com.example.demo.Scheduler.ScheduleDto.Request;
import com.example.demo.Scheduler.ScheduleDto.Response;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

public interface EmailScheduleService {


    public String createMailSchedule(Request request) throws SchedulerException;

    public Response getSchedule(int id);

    public List<Response> getSchedules(String username, int page, int size);


//    public String updateSchedule(Request request, ZonedDateTime zonedDateTime);

//    public void deleteSchedule(int id, String username);

    public boolean checkIfScheduleExists(String username, int id);

//    public void deleteEmailSchedule(Integer scheduleId);

//    Response sendInviteOpenTalk(Request request);
    Trigger buildJobTrigger(JobDetail jobDetail);

//    void schedule(JobData data);
}
