package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.invoice.Invoice;
import com.poojithairosha.vristopos.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findAllByDateTimeBetween(LocalDateTime localDateTime, LocalDateTime now, PageRequest pageRequest);

    @Query(value = "select p from Invoice i join InvoiceItem ii on i.id = ii.invoice.id join Stock s on s.id = ii.stock.id join Product p on p.id = s.product.id group by p.id order by count(*) desc limit 5")
    List<Product> findTopSellingProducts();
}
