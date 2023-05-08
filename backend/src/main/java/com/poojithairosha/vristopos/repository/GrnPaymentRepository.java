package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.grn.GrnPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrnPaymentRepository extends JpaRepository<GrnPayment, Long> {
}
