package com.poojithairosha.vristopos.dto;

import java.time.LocalDateTime;

public record SearchGrnInvoiceDTO(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {
}
