package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.SupplierDTO;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import com.poojithairosha.vristopos.service.SupplierService;
import com.poojithairosha.vristopos.service.impl.SupplierServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriProperties.URI_SUPPLIERS)
@RequiredArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<Supplier>> searchSuppliers(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        log.info("Start execute searchSuppliers");
        return ResponseEntity.ok(supplierService.searchSuppliers(page, size, text));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long id) {
        log.info("Start execute getSupplier with supplier id: {}", id);
        return ResponseEntity.ok(supplierService.getSupplier(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> registerSupplier(@RequestBody SupplierDTO supplier) {
        log.info("Start execute registerSupplier with supplier: {}", supplier);
        return ResponseEntity.ok(supplierService.registerSupplier(supplier));
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateSupplier(@RequestBody Supplier supplier) {
        log.info("Start execute updateSupplier with supplier: {}", supplier);
        return ResponseEntity.ok(supplierService.updateSupplier(supplier));
    }

}
