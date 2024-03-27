package com.example.bookapp.src.dto.user;

import com.example.bookapp.src.model.User;
import com.example.bookapp.src.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 30)
    private String password;
    @NotBlank
    @Length(min = 8, max = 30)
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<User.RoleName> roles;
}
