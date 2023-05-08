package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.AuthRequest;
import com.poojithairosha.vristopos.dto.AuthResponse;
import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ResetPasswordRequest;
import com.poojithairosha.vristopos.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ClientResponse> forgotPassword(@NotNull @RequestParam String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ClientResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }


}
