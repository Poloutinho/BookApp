package bookapp.service;

import bookapp.dto.user.UserRegistrationRequestDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto getByEmail(String email);
}
