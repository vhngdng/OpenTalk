package com.example.demo.service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.ENUM.ERole;
import com.example.demo.entity.UserRole.Role;

import java.util.Set;

public interface IRoleService {
    RoleDTO findById(Integer id);
    Set<Role> findAll();

    Role saveRole(Role role);

    Role findByName(ERole roleName);
}
