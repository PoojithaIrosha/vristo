package com.poojithairosha.vristopos.dto;

import com.poojithairosha.vristopos.model.product.Product;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GrnItemDTO(
        Product product,
        Integer quantity,
        double buyingPrice,
        double sellingPrice,
        LocalDate manufactureDate,
        LocalDate expireDate
) {
}
