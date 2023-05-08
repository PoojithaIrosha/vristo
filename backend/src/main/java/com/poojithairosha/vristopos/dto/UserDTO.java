package com.poojithairosha.vristopos.dto;

import com.poojithairosha.vristopos.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO(
        Long id,
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Mobile is mandatory")
        String mobile,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        LocalDateTime createdAt,
        @NotNull(message = "Role is mandatory")
        UserRole role,
        Boolean isEnabled
) {
}
