package com.poojithairosha.vristopos.model.grn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import com.poojithairosha.vristopos.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Grn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "grn", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("grn")
    private GrnPayment grnPayment;

    @OneToMany(mappedBy = "grn", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("grn")
    private List<GrnItem> grnItems;
}
