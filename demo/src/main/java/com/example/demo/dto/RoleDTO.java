package com.example.demo.dto;

import com.example.demo.ENUM.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer Id;
    private ERole name;
}
