package com.meraphorce.controllers;

import com.meraphorce.dto.UserDto;
import com.meraphorce.exceptions.GlobalExceptionHandler;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){

        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/catalogNames")
    public ResponseEntity<List<String>> getAllUserNames() {

        return ResponseEntity.ok(userService.getAllUserNames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id){

        UserDto user = userService.getUserById(id);

        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){

        if (userService.getUserById(id) != null) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto user) throws GlobalExceptionHandler {

        UserDto updatedUser = userService.updateUser(id, user);

        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UserDto>> createUsersInBatch(@RequestBody List<UserDto> users) throws GlobalExceptionHandler {

        List<UserDto> createdUsers = userService.createUsersInBatch(users);

        return new ResponseEntity<>(createdUsers, HttpStatus.CREATED);
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<String> ids) throws GlobalExceptionHandler {

        userService.deleteUsers(ids);

        return ResponseEntity.noContent().build();
    }

}
