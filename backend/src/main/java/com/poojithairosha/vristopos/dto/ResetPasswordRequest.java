package com.poojithairosha.vristopos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ResetPasswordRequest(@NotBlank(message = "Email is required") String email,
                                   @NotBlank(message = "Verification code is required") String verificationCode,
                                   @NotBlank(message = "Password is required") String password) {

}
