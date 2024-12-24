package com.secure.user.service;

import com.secure.user.dto.UserDto;
import com.secure.user.enums.AppRole;
import com.secure.user.exception.UserAlreadyExistsException;
import com.secure.user.exception.UserNotFoundException;
import com.secure.user.model.Role;
import com.secure.user.model.User;
import com.secure.user.repository.RoleRepository;
import com.secure.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDto(user);
    }

    @Override
    public UserDto createUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new UserAlreadyExistsException("User with username " + user.getUserName() + " already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
        }

        if (user.getRole() == null) {
            Role role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role USER not found"));
            user.setRole(role);
        }

        return convertToDto(userRepository.save(user));
    }

    @Override
    public void updateUserRole(Long userId, String roleName,User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!existingUser.getUserName().equals(updatedUser.getUserName()) && userRepository.existsByUserName(updatedUser.getUserName())) {
            throw new UserAlreadyExistsException("User with username " + updatedUser.getUserName() + " already exists.");
        }

        if (!existingUser.getEmail().equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + updatedUser.getEmail() + " already exists.");
        }

        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setAccountNonLocked(updatedUser.isAccountNonLocked());
        existingUser.setAccountNonExpired(updatedUser.isAccountNonExpired());
        existingUser.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
        existingUser.setEnabled(updatedUser.isEnabled());
        existingUser.setCredentialsExpiryDate(updatedUser.getCredentialsExpiryDate());
        existingUser.setAccountExpiryDate(updatedUser.getAccountExpiryDate());
        existingUser.setTwoFactorSecret(updatedUser.getTwoFactorSecret());
        existingUser.setTwoFactorEnabled(updatedUser.isTwoFactorEnabled());
        existingUser.setSignUpMethod(updatedUser.getSignUpMethod());
        AppRole appRole;
        try {
            appRole = AppRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role name: " + roleName);
        }
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        existingUser.setRole(role);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getTwoFactorSecret(),
                user.isTwoFactorEnabled(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdateDate()
        );
    }

}