package bookapp.mapper;

import bookapp.config.MapperConfig;
import bookapp.dto.user.UserResponseDto;
import bookapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toUserResponse(User savedUser);
}
