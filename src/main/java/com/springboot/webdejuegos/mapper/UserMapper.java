package com.springboot.webdejuegos.mapper;

import com.springboot.webdejuegos.dto.UserDto;
import com.springboot.webdejuegos.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getAvatar()
        );
    }

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getNickname(),
                userDto.getAvatar()
        );
    }
}
