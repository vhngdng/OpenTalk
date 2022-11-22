package com.example.demo.dto.Display;

import com.example.demo.dto.RoleDTO;
import com.example.demo.entity.Branch;
import com.example.demo.entity.OpenTalk;
import com.example.demo.entity.UserRole.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDisplayDTO {
    private Long id;
    private String userName;
    private String fullName;
    private Set<RoleDTO> roleDTOs;
    private String email;
    private boolean active;

}
