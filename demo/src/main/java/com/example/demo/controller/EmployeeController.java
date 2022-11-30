package com.example.demo.controller;


import com.example.demo.dto.Display.OpenTalkDisplayDTO;
import com.example.demo.service.Impl.EmployeeService;
import com.example.demo.service.Impl.OpenTalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
//@RequestMapping("/admin")
//@RequestMapping("${path_admin}")   // use properties file
@RequiredArgsConstructor
@Slf4j
//@PreAuthorize("hasRole('EMPLOYEE')")    //-> @PreAuthorize se override lai antmacher trong WebSecurityConfig

//@PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OpenTalkService openTalkService;
    @GetMapping("/opentalks")
    public ResponseEntity<List<OpenTalkDisplayDTO>> findAllOpenTalkDisplay(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        List<OpenTalkDisplayDTO> openTalkDisplayDTOS = openTalkService.findOpenTalkOfLoginUser(userPrincipal);
        return ResponseEntity.ok(openTalkDisplayDTOS);
    }
}
