package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.user.UserRegistrationRequestDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
