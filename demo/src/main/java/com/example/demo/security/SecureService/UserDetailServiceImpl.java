package com.example.demo.security.SecureService;


import com.example.demo.entity.Employee;
import com.example.demo.entity.UserRole.Role;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    private final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    @Transactional()
    public UserDetailImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("============================================================" + username);
        final Employee employee = employeeRepository.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println(employee.getUserName());
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username or email " + username));

        UserDetailImpl userDetailImpl =
//                (UserDetailImpl) User.withUsername(employee.getUserName()).password(employee.getPassword()).authorities("USER").build();
                new UserDetailImpl(employee.getUserName(), employee.getPassword(), mapRolesToAuthorities(employee.getRoles()));
        logger.info(userDetailImpl.getUsername());
        return userDetailImpl;
//        return new UserPrinciple(employee.getUserName(), employee.getPassword(), mapRolesToAuthorities(employee.getRoles()));
//                new org.springframework.security.core.userdetails.User(employee.getUserName(), employee.getPassword(), mapRolesToAuthorities(employee.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().toString())).collect(Collectors.toList());
    }
}
