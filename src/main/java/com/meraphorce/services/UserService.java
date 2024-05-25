package com.meraphorce.services;

import com.meraphorce.dto.UserDto;
import com.meraphorce.exceptions.GlobalExceptionHandler;
import com.meraphorce.models.User;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    List<UserDto> getAllUsers();

    List<String> getAllUserNames();

    UserDto getUserById(String id);

    void deleteUser(String id);

    UserDto updateUser(String id, UserDto user) throws GlobalExceptionHandler;

    List<UserDto> createUsersInBatch(List<UserDto> users) throws GlobalExceptionHandler;

    void deleteUsers(List<String> ids) throws GlobalExceptionHandler;

}

