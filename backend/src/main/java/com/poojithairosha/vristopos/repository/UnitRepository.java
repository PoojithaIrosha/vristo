package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.product.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
