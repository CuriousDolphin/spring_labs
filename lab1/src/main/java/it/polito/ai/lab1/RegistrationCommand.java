package it.polito.ai.lab1;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegistrationCommand {
    @Size(min=3,max=50,message="name size must be between 3 and 50") String name;
    @Email(message="Insert a valid email") String email;
    @Size(min=3,max=20, message="Password size must be between 3 and 20") String password1;
    @AssertTrue(message = "Accept privacy condition") boolean privacy;

    @Size(min=3,max=20, message="Password size must be between 3 and 20")  String password2;

}

