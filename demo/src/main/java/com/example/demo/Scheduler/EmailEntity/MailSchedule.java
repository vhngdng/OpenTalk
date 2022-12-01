package com.example.demo.Scheduler.EmailEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mail_schedule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "username")
    private String username;

    @Column(name = "schedule_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String scheduleDateTime;

    @Column(name = "schedule_zone_id")
    private String scheduleZoneId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
