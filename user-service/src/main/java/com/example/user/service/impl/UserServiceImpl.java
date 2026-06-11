package com.example.user.service.impl;

import com.example.user.entity.User;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import com.example.user.request.UserRequest;
import com.example.user.response.UserResponse;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) {

        log.info("Creating user email={}", request.email());

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(request.role());

        User savedUser = userRepository.save(user);

        log.info(
                "User created successfully id={}",
                savedUser.getId()
        );

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {

        log.info("Fetching user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        log.info("Fetching all users");

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Retrieved {} users", users.size());

        return users;
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {

        log.info("Updating user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(request.role());

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully id={}", updatedUser.getId());

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        log.info("Deleting user id={}", id);

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully id={}", id);
    }

    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}