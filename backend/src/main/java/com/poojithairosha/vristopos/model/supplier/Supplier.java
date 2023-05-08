package com.poojithairosha.vristopos.model.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String mobile;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnoreProperties("suppliers")
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}