package com.example.demo.service.EmailService.Impl;

import com.example.demo.CONST.Constant;
import com.example.demo.Scheduler.EmailEntity.MailSchedule;
import com.example.demo.Scheduler.ScheduleDto.Request;
import com.example.demo.Scheduler.ScheduleDto.Response;
import com.example.demo.Scheduler.job.EmailScheduleJob;
import com.example.demo.dto.OpenTalkDTO;
import com.example.demo.exception.InternalServerException;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.MailScheduleRepository;
import com.example.demo.repository.OpenTalkRepository;
import com.example.demo.service.EmailService.EmailScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Slf4j
//@RequiredArgsConstructor
//@PropertySource("classpath:/quartz.properties")
public class EmailScheduleServiceImpl implements EmailScheduleService {
  @Autowired
  private Scheduler scheduler;
  @Autowired
  private MapStructMapper mapper;
  @Autowired
  private MailScheduleRepository mailScheduleRepository;
  @Autowired
  private OpenTalkRepository openTalkRepository;


  @PostConstruct
  public void postConstruct() {
    try {
      log.info("Run job " + new Date());
      scheduler.start();
    } catch (SchedulerException ex) {
      log.error("scheduler throws exception " + ex);
    }
  }

  @PreDestroy
  public void preDestroy() {
    try {
      log.info("finish job " + new Date());
      scheduler.shutdown();
    } catch (SchedulerException ex) {
      log.error("scheduler throws exception " + ex);
    }
  }


  @Override
  @Transactional
  @Secured("ROLE_ADMIN")
  public String createMailSchedule(Request request) throws SchedulerException {
//    openTalkRepository.findNearestOpenTalk();
    JobDetail jobDetail = buildJobDetail(request, new OpenTalkDTO());
    log.info("descrip of jobDetail" + jobDetail.getDescription());
    Trigger cronTrigger = buildJobTrigger(jobDetail);
    log.info("descrip of trigger" + cronTrigger.getNextFireTime());

    MailSchedule mailSchedule = new MailSchedule();
    mailSchedule.setScheduleId(request.getScheduleId());
    mailSchedule.setScheduleZoneId(Constant.LANGUAGE_TAG);
    mailSchedule.setDeleted(false);
    mailSchedule.setScheduleZoneId(Constant.LANGUAGE_TAG);
    mailSchedule.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info(scheduler.getSchedulerName());
    Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
    mailSchedule.setScheduleDateTime(String.valueOf(date));
    log.info("Schedule is created ");
    mailScheduleRepository.save(mailSchedule);

    return saveSchedule(request);
  }


  @Override
  public Trigger buildJobTrigger(JobDetail jobDetail) {
    return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(Constant.triggerGroup)
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule(Constant.cronTest)
                    .withMisfireHandlingInstructionFireAndProceed()
                    .inTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault())))
            .build();
  }
  @Secured("ROLE_ADMIN")   //  == @PreAuthorize(“hasRole(‘ROLE_ADMIN')”)
  public JobDetail buildJobDetail(Request request, OpenTalkDTO openTalkDTO) {
    JobDataMap jobDataMap = new JobDataMap();
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("username from security context holder " + username);
    jobDataMap.put("from", username);
    jobDataMap.put("subject", request.getSubject());
    jobDataMap.put("text", request.getText());
    jobDataMap.put("scheduleId", request.getScheduleId());
    jobDataMap.put("openTalkDTO", openTalkDTO);
    return JobBuilder.newJob().ofType(EmailScheduleJob.class)
            .storeDurably()
            .withIdentity(Constant.jobGroup)
            .withDescription(Constant.jobDescription)
            .usingJobData(jobDataMap)
            .build();
  }

  public String saveSchedule(Request request) {
    try {
      MailSchedule save = mailScheduleRepository.save(mapper.toMailSchedule(request));
      return save.getScheduleId().toString();
    } catch (Exception e) {
      throw new InternalServerException("Unable to save schedule to DB");
    }
  }

  public JobDetail getJobDetail(String scheduleId, Request request) {
    Integer jobId = Integer.valueOf(scheduleId);
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("subject", request.getSubject());
    jobDataMap.put("message", request.getText());
    jobDataMap.put("scheduleId  ", scheduleId);

    return JobBuilder.newJob(EmailScheduleJob.class)
            .withIdentity(Constant.jobGroup)
            .withDescription(Constant.jobDescription)
            .usingJobData(jobDataMap)
            .storeDurably()
            .build();
  }

//  @Override
//  public void schedule(JobData data) {
//    String jobName = data.getJobName();
//    String jobGroup = data.getJobGroup();
//    int counter = data.getCounter();
//    int gapDuration = data.getGapDuration();
//    ZonedDateTime zonedDateTime = ZonedDateTime.of(data.getStartTime(), ZoneId.of(Constant.LANGUAGE_TAG));
//    JobDataMap dataMap = new JobDataMap();
//    dataMap.put("test", "this is just for demo");
//    JobDetail detail = JobBuilder.newJob(TestScheduleJob.class)
//            .withIdentity(jobName, jobGroup)
//            .usingJobData(dataMap)
//            .build();
//    Trigger trigger = TriggerBuilder.newTrigger()
//            .withIdentity(jobName, jobGroup)
//            .startAt(Date.from(zonedDateTime.toInstant()))
//            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                    .withIntervalInMinutes(gapDuration)
//                    .withRepeatCount(counter))
//            .build();
//    try {
//      scheduler.scheduleJob(detail, trigger);
//    } catch (SchedulerException e) {
//      throw new RuntimeException(e);
//    }
//  }

//    public Trigger getSimpleTrigger(JobDetail jobDetail, ZonedDateTime zonedDateTime) {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity(jobDetail.getKey().getName(), Constant.triggerGroup)
//                .withDescription(Constant.triggerDescription)
//                .startAt(Date.from(zonedDateTime.toInstant()))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
//                .build();
//    }

//    public CronTrigger getCronTrigger(String job) throws SchedulerException {
//       return (CronTrigger) scheduler.getTrigger(new TriggerKey(Constant.jobGroup));
//    }


  @Override
  public Response getSchedule(int scheduleId) {
    MailSchedule mailSchedule = mailScheduleRepository.findByScheduleIdAndIsDeletedFalse(scheduleId)
            .orElseThrow(() -> new InternalServerException("Error fetching the schedule"));
    return mapper.toResponse(mailSchedule);
  }

  // paging
  @Override
  public List<Response> getSchedules(String username, int page, int size) {
    List<Response> responses = new ArrayList<>();
    try {
      Pageable pageable = PageRequest.of(page, size);
      Page<MailSchedule> schedules = mailScheduleRepository.findByUsernameAndIsDeletedFalse(username, pageable);
      List<MailSchedule> mailSchedules = schedules.getContent();
      responses = mapper.getResponseFromMailSchedule(mailSchedules);
    } catch (Exception e) {
      throw new InternalServerException("Unable to fetch all schedules from DB");
    }
    return responses;
  }

//    @Override
//    @Transactional
//    public void deleteSchedule(int scheduleId, String username) {
//        deleteEmailSchedule(scheduleId);
//        deleteJobAndTrigger(scheduleId);
//    }

//    public void deleteEmailSchedule(Integer scheduleId) {
//        try {
//            Optional<MailSchedule> optional = mailScheduleRepository.findById(scheduleId);
//            if (optional.isPresent()) {
//                MailSchedule mailSchedule = optional.get();
//                mailSchedule.setDeleted(true);
//                mailScheduleRepository.save(mailSchedule);
//            }
//        } catch (Exception e) {
//            throw new InternalServerException("Error deleting schedule");
//        }
//    }



  public void deleteJobAndTrigger(Integer scheduleId) {
    try {
      scheduler.unscheduleJob(new TriggerKey(scheduleId.toString(), Constant.triggerDescription));
      scheduler.deleteJob(new JobKey(scheduleId.toString(), Constant.jobDescription));
    } catch (SchedulerException schedulerException) {
      throw new InternalServerException("Unable to delete the job from scheduler");
    }
  }

  @Override
  public boolean checkIfScheduleExists(String username, int id) {
    try {
      return mailScheduleRepository.existsByUsernameAndScheduleIdAndIsDeletedFalse(username, id);
    } catch (Exception e) {
      throw new InternalServerException("Error checking if schedule exists");
    }
  }

//    @Override
//    @Transactional
//    public String updateSchedule(Request request, ZonedDateTime zonedDateTime) {
//        updateMailSchedule(request);
//        JobDetail jobDetail = updateJobDetail(request);
//        updateTriggerDetails(request, jobDetail, zonedDateTime);
//        return request.getScheduleId().toString();
//    }

//    public void updateMailSchedule(Request request) {
//        try {
//            Optional<MailSchedule> optional = mailScheduleRepository.findByScheduleIdAndIsDeletedFalse(request.getScheduleId());
//            if (optional.isPresent()) {
//                MailSchedule mailSchedule = optional.get();
////                mailSchedule.setUsername(request.getUsername());
////                mailSchedule.setScheduleDateTime(request.getScheduledTime().toString());
////                mailSchedule.setScheduleZoneId(request.getZoneId().toString());
//                mailScheduleRepository.save(mailSchedule);
//            }
//        } catch (Exception e) {
//            throw new InternalServerException("Unable to update the schedule in DB");
//        }
//    }

//    public JobDetail updateJobDetail(Request request) {
//        JobDetail jobDetail = null;
//        try {
//            if (request.getScheduleId() != null) {
//                jobDetail = scheduler.getJobDetail(new JobKey(request.getScheduleId().toString(), Constant.jobGroup));
////                jobDetail.getJobDataMap().put("scheduledTime", request.getScheduledTime());
//                jobDetail.getJobDataMap().put("subject", request.getSubject());
//                jobDetail.getJobDataMap().put("message", request.getText());
//                scheduler.addJob(jobDetail, true);
//            }
//        } catch (SchedulerException schedulerException) {
//            throw new InternalServerException("Unable to update the job data map");
//        }
//        return jobDetail;
//    }

//    public void updateTriggerDetails(Request request, JobDetail jobDetail, ZonedDateTime zonedDateTime) throws SchedulerException {
//        Trigger newTrigger = (Trigger) scheduler.getTriggersOfJob(JobKey.jobKey(jobDetail.getKey().getName()));
//        try {
//            scheduler.rescheduleJob(new TriggerKey(request.getScheduleId().toString(), Constant.triggerGroup), newTrigger);
//        } catch (SchedulerException schedulerException) {
//            throw new InternalServerException("Unable to update the trigger in DB");
//        }
//    }
}
