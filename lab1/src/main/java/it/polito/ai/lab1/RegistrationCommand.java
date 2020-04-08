package it.polito.ai.lab1;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegistrationCommand {
    @Size(min = 3, max = 50, message = "Name size must be between 3 and 50")
    private String name;
    @Email(message = "Insert a valid email")
    private String email;
    @Size(min = 3, max = 20, message = "Password size must be between 3 and 20")
    private String password1;
    @AssertTrue(message = "Please accept privacy condition")
    private boolean privacy;

    @Size(min = 3, max = 20, message = "Password size must be between 3 and 20")
    private String password2;

}

