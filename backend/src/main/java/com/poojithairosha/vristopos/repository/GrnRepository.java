package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.grn.Grn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GrnRepository extends JpaRepository<Grn, Long> {
        Page<Grn> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end, PageRequest pageRequest);
}
