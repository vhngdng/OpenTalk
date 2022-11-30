package com.example.demo.service;

import com.example.demo.ENUM.ERole;
import com.example.demo.dto.Display.AccountDisplayDTO;
import com.example.demo.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {
    EmployeeDTO findById(long id);
    EmployeeDTO save(EmployeeDTO employeeDTO);
    List<EmployeeDTO> findAllEmployees();

    Page<EmployeeDTO> findAllWithPagination(Integer limit, Integer page);

    List<AccountDisplayDTO> findAllAccount();

    void deleteById(long id);

    boolean duplicateName(String name);

    boolean duplicateEmail(String email);


    void addRoleToUser(String username, ERole role);

    List<EmployeeDTO> findAllAdminAccount();

//    List<String> findAllEmailExceptHost(Long id);
//
//    List<String> findAllFullNameExceptHost(Long id);
    List<EmployeeDTO> findAllExceptHost(Long id);

    long size();
}
