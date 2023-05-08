package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.user.User;
import com.poojithairosha.vristopos.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        return ResponseEntity.ok(userService.searchUsers(page, size, text));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@NotNull @PathVariable(name = "id") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("update-status/{id}")
    public ResponseEntity<ClientResponse> updateUserStatus(@NotNull @PathVariable(name = "id") String userId) {
        return ResponseEntity.ok(userService.updateUserStatus(userId));
    }

    @PutMapping
    public ResponseEntity<ClientResponse> updateUserDetails(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserDetails(userDTO));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }
}
