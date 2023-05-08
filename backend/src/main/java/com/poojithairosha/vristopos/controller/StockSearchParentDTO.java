package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.StockSearchDTO;
import lombok.Builder;

@Builder
public record StockSearchParentDTO(
        StockSearchDTO stock,
        int page,
        int size
) {
}
