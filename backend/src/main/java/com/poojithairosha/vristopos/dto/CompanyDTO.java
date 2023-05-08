package com.poojithairosha.vristopos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyDTO(
        Long id,
        @NotBlank(message = "Company name is mandatory")
        String name,
        @NotBlank(message = "Mobile number is mandatory")
        String mobile,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email is invalid")
        String email,
        @NotBlank(message = "Address is mandatory")
        String address) {
}
