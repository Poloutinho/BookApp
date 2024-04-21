package bookapp.mapper;

import bookapp.config.MapperConfig;
import bookapp.model.User;
import bookapp.dto.user.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toUserResponse(User savedUser);
}
