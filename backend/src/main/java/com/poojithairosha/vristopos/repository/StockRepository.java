package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.model.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    boolean existsByProductAndManufactureDateAndExpireDateAndSellingPrice(
            Product product,
            LocalDate manufactureDate,
            LocalDate expireDate,
            Double sellingPrice
    );

    Optional<Stock> findByProductAndManufactureDateAndExpireDateAndSellingPrice(
            Product product,
            LocalDate manufactureDate,
            LocalDate expireDate,
            Double sellingPrice
    );

    List<Stock> findAllByQuantityGreaterThan(int quantity);
}
