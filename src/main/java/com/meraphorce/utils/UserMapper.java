package com.meraphorce.utils;

import com.meraphorce.dto.UserDto;
import com.meraphorce.models.User;

public class UserMapper {

    public static User requestToEntity(UserDto userRequestDto) {
        return User.builder()
                .id(userRequestDto.getId())
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();
    }


    public static UserDto entityToResponse(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
