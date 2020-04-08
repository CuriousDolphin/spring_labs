package it.polito.ai.lab1;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginCommand {
    @NotEmpty(message = "email must not be empty")
    @Email
    private String email;
    @NotEmpty(message = "password must not be empty")
    private String password;
}