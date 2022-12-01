package com.example.demo.Scheduler.job;

import com.example.demo.Scheduler.EmailEntity.Email;
import com.example.demo.service.EmailService.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@RequiredArgsConstructor
public class EmailScheduleJob extends QuartzJobBean {
    private final EmailService emailService;


    @Async
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("job ** {} fired {} @{}", context.getJobDetail().getKey().getName(), context.getFireTime());
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String from = jobDataMap.getString("from");
        String subject = jobDataMap.getString("subject");
        String text = jobDataMap.getString("text");
//        OpenTalkDTO openTalkDTO = jobDataMap.get()
//        int scheduleId = jobDataMap.getInt("scheduleId");
        Email email = new Email();
        email.setFrom(from);
        email.setSubject(subject);
        email.setText(text);
//        email.setBcc();


//        emailService.sendMail(email, openTalkDTO);
//        scheduleService.deleteEmailSchedule(scheduleId);
    }


}
