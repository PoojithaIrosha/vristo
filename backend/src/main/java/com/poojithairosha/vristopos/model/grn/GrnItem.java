package com.poojithairosha.vristopos.model.grn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poojithairosha.vristopos.model.product.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class GrnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double buyingPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "grn_id")
    @JsonIgnore
    private Grn grn;
}
