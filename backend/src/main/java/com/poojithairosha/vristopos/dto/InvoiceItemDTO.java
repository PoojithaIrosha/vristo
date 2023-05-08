package com.poojithairosha.vristopos.dto;

import com.poojithairosha.vristopos.model.product.Stock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InvoiceItemDTO(
        @NotBlank(message = "Quantity is required")
        Integer quantity,
        @NotBlank(message = "Expire date is required")
        LocalDate expireDate,
        @NotNull(message = "Stock is required")
        Stock stock,
        @NotBlank(message = "Total is required")
        Double total
) {
}
