package it.polito.ai.lab1;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginCommand {
    @NotEmpty String name;
    @NotEmpty String password;
}