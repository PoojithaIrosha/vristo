package com.poojithairosha.vristopos.dto;

import jakarta.validation.constraints.NotBlank;

public record SupplierDTO(
        @NotBlank(message = "Supplier name is mandatory") String name,
        @NotBlank(message = "Supplier email is mandatory") String email,
        @NotBlank(message = "Supplier mobile is mandatory") String mobile,
        @NotBlank(message = "Supplier company is mandatory") Long company
) {
}
