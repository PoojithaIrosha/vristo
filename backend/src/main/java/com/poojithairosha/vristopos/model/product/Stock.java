package com.poojithairosha.vristopos.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double sellingPrice;
    private LocalDate manufactureDate;
    private LocalDate expireDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
