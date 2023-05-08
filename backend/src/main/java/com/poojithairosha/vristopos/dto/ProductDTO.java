package com.poojithairosha.vristopos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(Long id, @NotBlank(message = "Product name is mandatory") String name,
                         @NotNull(message = "Product's brand is mandatory") Long brand,
                         @NotNull(message = "Product's unit is mandatory") Long unit) {
}
