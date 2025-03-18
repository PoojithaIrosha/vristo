package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.repository.UserRepository;
import com.poojithairosha.vristopos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDTO> getAllUsers(int page, int size) {
        log.info("Start getAllUsers");
        PageRequest pr = PageRequest.of(page, size);
        Page<UserDTO> result = userRepository.findAll(pr).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled()));
        log.info("Finished searching all users and found {} users", result.getTotalElements());
        return result;
    }

    @Override
    public ClientResponse updateUserStatus(String userId) {
        log.info("Start updateUserStatus");
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setIsEnabled(!user.getIsEnabled());
        userRepository.save(user);
        log.info("User status updated successfully for user: {}", userId);
        return new ClientResponse(true, "User status updated successfully");
    }

    @Override
    public UserDTO getUserById(String userId) {
        log.info("Start getUserById by id {}", userId);
        return userRepository.findById(Long.parseLong(userId)).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled())).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Page<UserDTO> searchUsers(int page, int size, String text) {
        log.info("Start searchUsers by text: {}", text);
        PageRequest pr = PageRequest.of(page, size);
        Page<UserDTO> result = userRepository.findAllByNameContainingOrEmailContainingOrMobileContaining(text, text, text, pr).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled()));
        log.info("Finished searching users and found {} users", result.getTotalElements());
        return result;
    }

    @Override
    public ClientResponse updateUserDetails(UserDTO userDTO) {
        log.info("Start updateUserDetails of user: {}", userDTO);
        User user = userRepository.findById(userDTO.id()).orElseThrow(() -> new UsernameNotFoundException("User not found with the given credentials"));
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setMobile(userDTO.mobile());
        user.setRole(userDTO.role());
        userRepository.save(user);
        log.info("User details updated successfully for user: {}", userDTO.id());
        return new ClientResponse(true, "User details updated successfully");
    }

    @Override
    public ClientResponse registerUser(User user) {
        log.info("Start registerUser with email: {}", user.getEmail());
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new RuntimeException("User already exists");
        });

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });

        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("User registered successfully for user: {}", user.getEmail());
        return new ClientResponse(true, "User registered successfully");
    }
}
