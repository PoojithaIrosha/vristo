package com.poojithairosha.vristopos.dto;

import com.poojithairosha.vristopos.model.grn.GrnPayment;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GrnDTO(
        @NotNull(message = "Supplier is required")
        Supplier supplier,
        @NotNull(message = "Grn Payment is required")
        GrnPayment grnPayment,
        @NotNull(message = "Grn Items are required")
        List<GrnItemDTO> grnItems
) {
}
