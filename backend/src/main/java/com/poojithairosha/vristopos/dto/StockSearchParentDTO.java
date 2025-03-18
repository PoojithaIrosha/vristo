package com.poojithairosha.vristopos.dto;

import lombok.Builder;

@Builder
public record StockSearchParentDTO(
        StockSearchDTO stock,
        int page,
        int size
) {
}
