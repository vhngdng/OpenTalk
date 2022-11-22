package com.example.demo.dto;

import com.example.demo.Audit.AuditDTO.AuditDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Long id;

    @Size(min = 4, message = "Độ dài kí tự > 4")
    private String userName;
    @Size(min = 4, message = "Độ dài kí tự > 10")
    private String fullName;
    @Email
    private String email;

    private String password;

    private Set<RoleDTO> roleDTOs;

    private boolean active;

    private BranchDTO branchDTO;

//    private LocalDateTime createdAT;
    private AuditDTO auditDTO;
}
