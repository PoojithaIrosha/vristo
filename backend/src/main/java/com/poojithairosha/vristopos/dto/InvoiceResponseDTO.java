package com.poojithairosha.vristopos.model.invoice;

import com.poojithairosha.vristopos.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceResponseDTO {
    private Long id;

    private LocalDateTime dateTime;

    private UserDTO user;

    private List<InvoiceItem> invoiceItems;

    private InvoicePayment invoicePayment;

}
