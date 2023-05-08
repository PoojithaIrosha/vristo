package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, PageRequest pageRequest);

    boolean existsByNameAndBrand_Id(String name, Long brand);
}

