package com.example.demo.controller.AuthoController;

import com.example.demo.dto.Login.LoginDTO;
import com.example.demo.security.Jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;
  //    @Autowired
//    private AuthenticationManagerBuilder authenticationManagerBuilder;
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  @Autowired
  private JwtTokenUtil jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<String> authenticationUser(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
    logger.info("=========================================================================");
    System.out.println(loginDTO.getUsername());
    Authentication authentication =
            authenticationConfiguration.getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
    System.out.println("point 0 ==========================================================");
    String jwt = new JwtTokenUtil().generateAccessToken(authentication);
    logger.info("jwt from controller" + jwt);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    System.out.println("point1 1 ===================================================");

    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION, jwt).body("Bearer Token: " + jwt);
  }
}
