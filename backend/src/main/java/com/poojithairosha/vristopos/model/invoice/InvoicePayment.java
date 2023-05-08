package com.poojithairosha.vristopos.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poojithairosha.vristopos.model.grn.PaymentType;
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
public class InvoicePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double payment;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;
}
