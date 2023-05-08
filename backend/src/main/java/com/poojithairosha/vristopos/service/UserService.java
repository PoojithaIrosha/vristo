package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.repository.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;

    public Page<UserDTO> getAllUsers(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled()));
    }

    public ClientResponse updateUserStatus(String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setIsEnabled(!user.getIsEnabled());
        userRepository.save(user);
        log.info("User status updated successfully for user: {}", userId);
        return new ClientResponse(true, "User status updated successfully");
    }

    public UserDTO getUserById(String userId) {
        return userRepository.findById(Long.parseLong(userId)).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled())).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Page<UserDTO> searchUsers(int page, int size, String text) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAllByNameContainingOrEmailContainingOrMobileContaining(text, text, text, pr).map(user -> new UserDTO(user.getId(), user.getName(), user.getMobile(), user.getEmail(), user.getCreatedAt(), user.getRole(), user.getIsEnabled()));
    }

    public ClientResponse updateUserDetails(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.id()).orElseThrow(() -> new UsernameNotFoundException("User not found with the given credentials"));
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setMobile(userDTO.mobile());
        user.setRole(userDTO.role());
        userRepository.save(user);
        log.info("User details updated successfully for user: {}", userDTO.id());
        return new ClientResponse(true, "User details updated successfully");
    }

    public ClientResponse registerUser(User user) {

        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new RuntimeException("User already exists");
        });

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });

        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("User registered successfully for user: {}", user.getId());
        return new ClientResponse(true, "User registered successfully");
    }
}
