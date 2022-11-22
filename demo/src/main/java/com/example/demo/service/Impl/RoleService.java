package com.example.demo.service.Impl;

import com.example.demo.dto.RoleDTO;
import com.example.demo.ENUM.ERole;
import com.example.demo.entity.UserRole.Role;
import com.example.demo.mapper.MapStructMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
//@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = {Exception.class, Throwable.class})
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    //requiredArgsConstructor di voi attribute final
    private final RoleRepository roleRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public RoleDTO findById(Integer id) {
        return mapStructMapper.toDTO(roleRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("this role is not existed" + roleName));

    }
}
