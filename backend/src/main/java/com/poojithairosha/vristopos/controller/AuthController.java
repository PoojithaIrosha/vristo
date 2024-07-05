package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.AuthRequest;
import com.poojithairosha.vristopos.dto.AuthResponse;
import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ResetPasswordRequest;
import com.poojithairosha.vristopos.service.AuthService;
import com.poojithairosha.vristopos.util.UriProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriProperties.URI_AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping(UriProperties.URI_LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        log.info("Login request for user: {}", authRequest.username());
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping(UriProperties.URI_FP)
    public ResponseEntity<ClientResponse> forgotPassword(@NotNull @RequestParam String email) {
        log.info("Forgot password request: {}", email);
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PostMapping(UriProperties.URI_RP)
    public ResponseEntity<ClientResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request: {}", request);
        return ResponseEntity.ok(authService.resetPassword(request));
    }

}
