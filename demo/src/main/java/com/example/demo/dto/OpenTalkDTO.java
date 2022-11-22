package com.example.demo.dto;

import com.example.demo.Audit.AuditDTO.AuditDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@AllArgsConstructor

public class OpenTalkDTO{
    private Long id;

    private String topicName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private BranchDTO branchDTO;

    private String linkMeeting;

    private EmployeeDTO employeeDTO;

    private AuditDTO auditDTO;


}
