package com.meraphorce.services;

import com.meraphorce.dto.UserDto;
import com.meraphorce.exceptions.CustomException;
import com.meraphorce.exceptions.GlobalExceptionHandler;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import com.meraphorce.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.meraphorce.utils.ValidatorRequired.validateUser;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto createUser(UserDto user){
        User newUser = UserMapper.requestToEntity(user);

        if (userRepository.existsByNameOrEmail(
                newUser.getName(),
                newUser.getEmail()))
        {
            throw new CustomException(
                    "User " + newUser.getName() + " already exists");
        }

        return UserMapper.entityToResponse(userRepository.save(newUser));
    }

    public List<UserDto> getAllUsers(){

        return userRepository.findAll().stream()
                .map(UserMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllUserNames() {
        return userRepository.findAll().stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(String id){

        User userDb = userRepository.findById(id).orElse(null);

        return UserMapper.entityToResponse(userDb);
    }

    public UserDto updateUser(String id, UserDto user) throws GlobalExceptionHandler {
        Optional<User> optionalExistingUser = userRepository.findById(id);

        if(optionalExistingUser.isPresent()){
            User updateUser = optionalExistingUser.get();
            updateUser.setName(user.getName());
            updateUser.setEmail(user.getEmail());

            return UserMapper.entityToResponse(userRepository.save(updateUser));

        } else {
            throw new CustomException("User not found");
        }
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<UserDto> createUsersInBatch(List<UserDto> users) throws GlobalExceptionHandler {

        if (users.size() == 0) {
            throw new CustomException("No users provided");
        }

        for (UserDto user : users) {

            validateUser(user);

            if (userRepository.existsByNameOrEmail( user.getName(), user.getEmail()))
            {
                throw new CustomException("User " + user.getName() + " already exists");
            }
        }

        return users.stream()
                .map(UserMapper::requestToEntity)
                .map(userRepository::save)
                .map(UserMapper::entityToResponse)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteUsers(List<String> ids) throws GlobalExceptionHandler {

        List<User> users = userRepository.findAllById(ids);

        if (users.size() != ids.size()) {
            throw new CustomException("All users not found");
        }

        userRepository.deleteAllById(ids);
    }


}
