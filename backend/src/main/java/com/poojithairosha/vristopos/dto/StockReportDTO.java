package com.poojithairosha.vristopos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockReportDTO {
    private String stockId;
    private String name;
    private String brand;
    private String price;
    private String qty;
    private String exd;
}

