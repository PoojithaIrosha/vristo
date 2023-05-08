package com.poojithairosha.vristopos.dto;

import com.poojithairosha.vristopos.model.invoice.InvoicePayment;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InvoiceDTO(
        @NotNull(message = "Invoice Payment is required")
        InvoicePayment invoicePayment,

        @NotNull(message = "Invoice Items is required")
        List<InvoiceItemDTO> invoiceItems
) {
}
