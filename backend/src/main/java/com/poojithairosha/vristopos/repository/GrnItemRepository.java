package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.grn.GrnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrnItemRepository extends JpaRepository<GrnItem, Long> {
}
