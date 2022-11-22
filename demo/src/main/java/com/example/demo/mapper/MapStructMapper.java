package com.example.demo.mapper;

import com.example.demo.Audit.Audit;
import com.example.demo.Audit.AuditDTO.AuditDTO;
import com.example.demo.dto.*;
import com.example.demo.dto.Display.AccountDisplayDTO;
import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.UserRole.Role;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")          // what is componentModel, why value = "spring"
public interface MapStructMapper {

    Branch toEntity(BranchDTO branchDTO);

    BranchDTO toDTO(Branch branch);

    RoleDTO toDTO(Role role);
//        RoleDTO roleDTO = new RoleDTO();
//        if (role.getId() == 1) {
//            roleDTO.setId(1);
//            roleDTO.setName("admin");
//        } else if (role.getId() == 2) {
//            roleDTO.setId(2);
//            roleDTO.setName("employee");
//        }
//        return roleDTO;
//
//    }

    Role toEntity(RoleDTO roleDTO);
//        Role role = new Role();
//        if (roleDTO.getId() == 1) {
//            role.setId(1);
//            role.setName("admin");
//        } else if (roleDTO.getId() == 2) {
//            role.setId(2);
//            role.setName("employee");
//        }
//        return role;
//    }

    Set<RoleDTO> toDTO(Set<Role> roles);

    Set<Role> toEntity(Set<RoleDTO> roleDTOS);

    @Mapping(target = "branch", source = "branchDTO")
    @Mapping(target = "roles", source = "roleDTOs")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Mapping(target = "branchDTO", source = "branch")
    @Mapping(target = "roleDTOs", source = "roles")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EmployeeDTO toDTO(Employee employee);

    @Mapping(target = "branch", source = "branchDTO")
//    @Mapping(target = "hostOpenTalk", source = "hostOpenTalkDTO")
    @Mapping(target = "employee", source = "employeeDTO")
    @Mapping(target = "audit", source = "auditDTO")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OpenTalk toEntity(OpenTalkDTO openTalkDTO);

    @Mapping(target = "branchDTO", source = "branch")
//    @Mapping(target = "hostOpenTalkDTO", source = "hostOpenTalk")
    @Mapping(target = "employeeDTO", source = "employee")
    @Mapping(target = "auditDTO", source = "audit")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OpenTalkDTO toDTO(OpenTalk openTalk);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "audit", source = "auditDTO")
    void updateEmployeeFromDTO(EmployeeDTO employeeDTO, @MappingTarget Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "audit", source = "auditDTO")
    void updateOpenTalkFromDTO(OpenTalkDTO openTalkDTO, @MappingTarget OpenTalk openTalk);

    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);

    List<OpenTalkDTO> toOpenTalkDTOs(List<OpenTalk> openTalks);

    List<BranchDTO> toBranchDTOs(List<Branch> branches);

    @Mapping(target = "branch",
            expression = "java(openTalk.getBranch().getName())")
    OpenTalkDisplayDTO toDisplayDTO(OpenTalk openTalk);

    List<OpenTalkDisplayDTO> toDisplayDTO(List<OpenTalk> openTalks);

    @Mapping(target = "roleDTOs", source = "roles")
    AccountDisplayDTO toDisplayDTO(Employee employee);

    List<AccountDisplayDTO> toListAccountDisplayDTO(List<Employee> employees);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuditDTO toDTO (Audit audit);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Audit toEntity (AuditDTO auditDTO);
}
