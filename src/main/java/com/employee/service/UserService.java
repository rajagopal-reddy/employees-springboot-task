package com.employee.service;

import com.employee.dto.UserDto;
import com.employee.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    UserDto getUserById(Long id);

    void updateUserRole(Long userId, String roleNme);
}