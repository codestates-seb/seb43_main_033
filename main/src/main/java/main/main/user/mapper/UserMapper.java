package main.main.user.mapper;

import main.main.user.dto.UserDto;
import main.main.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User userPostToUser(UserDto.Post requestBody);
    UserDto.Response userToUserResponse(User user);
    User resposerPatchToUser(UserDto.Patch requestBody, long authenticationUserId);

    UserDto.Response userPatchToUser(User user);
}
