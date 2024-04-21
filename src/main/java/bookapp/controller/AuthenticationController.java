package bookapp.controller;

import bookapp.dto.user.UserLoginRequestDto;
import bookapp.dto.user.UserLoginResponseDto;
import bookapp.dto.user.UserRegistrationRequestDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.RegistrationException;
import bookapp.security.AuthenticationService;
import bookapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users management", description = "Endpoints for managing")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Add new user", description = "Add new user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login user")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto)
            throws RegistrationException {
        return authenticationService.authenticate(requestDto);
    }
}
