package com.example.bookapp.src.controller;

import com.example.bookapp.src.dto.user.UserLoginRequestDto;
import com.example.bookapp.src.dto.user.UserRegistrationRequestDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.RegistrationException;
import com.example.bookapp.src.security.AuthenticationService;
import com.example.bookapp.src.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody UserLoginRequestDto requestDto)
            throws RegistrationException {
        return authenticationService.authenticate(requestDto);
    }
}
