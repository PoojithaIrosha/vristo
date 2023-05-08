package com.poojithairosha.vristopos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceReport {
    private String itemCode;
    private String productName;
    private String price;
    private String quantity;
    private String total;
}
