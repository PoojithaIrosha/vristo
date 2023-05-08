package com.poojithairosha.vristopos.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poojithairosha.vristopos.model.product.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;
}
