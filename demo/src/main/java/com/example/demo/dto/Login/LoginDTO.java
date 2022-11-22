package com.example.demo.dto.Login;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotNull(message = "Username or password is not null")
    @Size(min = 8, max = 64, message = "The length of username or email is not valid")
    private String username;
    @NotNull(message = "Username or password is not null")
    @Size(min = 8, max = 64, message = "The length of password is not valid")
    private String password;
}
