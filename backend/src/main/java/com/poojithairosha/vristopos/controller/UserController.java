package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.service.UserService;
import com.poojithairosha.vristopos.service.impl.UserServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_USERS)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(UriProperties.URI_FIND_ALL)
    public ResponseEntity<Page<UserDTO>> findAllUsers(@RequestParam int page, @RequestParam int size) {
        log.info("Start execute findAllUsers");
        Page<UserDTO> allUsers = userService.getAllUsers(page, size);
        log.info("Finished execute findAllUsers, found {} users", allUsers.getTotalElements());
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        log.info("Start search users by text {}", text);
        return ResponseEntity.ok(userService.searchUsers(page, size, text));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@NotNull @PathVariable(name = "id") String userId) {
        log.info("Start finding user by id: {}", userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping(UriProperties.URI_USERS_UPDATE_STATUS)
    public ResponseEntity<ClientResponse> updateUserStatus(@NotNull @PathVariable(name = "id") String userId) {
        log.info("Start updating user status: {}", userId);
        return ResponseEntity.ok(userService.updateUserStatus(userId));
    }

    @PutMapping
    public ResponseEntity<ClientResponse> updateUserDetails(@Valid @RequestBody UserDTO userDTO) {
        log.info("Start updating user details: {}", userDTO);
        return ResponseEntity.ok(userService.updateUserDetails(userDTO));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> registerUser(@Valid @RequestBody User user) {
        log.info("Start registering user: {}", user);
        return ResponseEntity.ok(userService.registerUser(user));
    }

}
