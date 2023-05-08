package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.supplier.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findByEmailContainingOrNameContainingOrMobileContaining(String email, String name, String mobile, PageRequest pr);

    boolean existsByEmailOrNameOrMobile(String email, String name, String mobile);
}
