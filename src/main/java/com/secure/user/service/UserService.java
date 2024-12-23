package com.secure.user.service;

import com.secure.user.dto.UserDto;
import com.secure.user.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    UserDto getUserById(Long id);

    void updateUserRole(Long userId, String roleNme);
}