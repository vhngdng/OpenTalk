package com.example.demo.Audit.AuditDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditDTO {
    private LocalDateTime createdAt;

    private LocalDateTime lastUpdate;

    private String userName;
}
