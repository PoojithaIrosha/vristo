package com.poojithairosha.vristopos.model.grn;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class GrnPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double payment;
    private Double balance;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToOne
    @JoinColumn(name = "grn_id")
    @JsonIgnore
    private Grn grn;
}
