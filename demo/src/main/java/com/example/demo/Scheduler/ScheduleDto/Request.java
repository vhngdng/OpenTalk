package com.example.demo.Scheduler.ScheduleDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private Integer scheduleId;

//    @NotNull(message = "username cannot be null")
//    @Null
    @JsonIgnoreProperties
    private String username;

    @NotNull(message = "subject cannot be null")
    private String subject;

    @NotNull(message = "message cannot be null")
    private String text;

//    @NotNull(message = "scheduledTime cannot be null")
//    private LocalDateTime scheduledTime;

//    @NotNull(message = "zoneId cannot be null")
//    private ZoneId zoneId;

//    public MailSchedule toMailSchedule() {
//        MailSchedule mailSchedule = new MailSchedule();
//        mailSchedule.setDeleted(false);
//        mailSchedule.setScheduleId(this.scheduleId);
//        mailSchedule.setUsername(this.username);
//        mailSchedule.setToEmail(this.toEmail);
//        mailSchedule.setScheduleDateTime(this.scheduledTime.toString());
//        mailSchedule.setScheduleZoneId(this.zoneId.toString());
//        return mailSchedule;
//    }

}
