package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.service.BrandService;
import com.poojithairosha.vristopos.service.impl.BrandServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_BRANDS)
@RequiredArgsConstructor
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands() {
        log.info("Start execute getBrands");
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<Brand>> searchBrands(@RequestParam String text, @RequestParam int page, @RequestParam int size) {
        log.info("Start execute searchBrands");
        return ResponseEntity.ok(brandService.searchBrands(text, page, size));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        log.info("Start execute getBrand");
        return ResponseEntity.ok(brandService.getBrand(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> createBrand(@RequestBody Brand brand) {
        log.info("Start execute createBrand");
        return ResponseEntity.ok(brandService.createBrand(brand));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateBrand(@RequestBody Brand brand) {
        log.info("Start execute updateBrand");
        return ResponseEntity.ok(brandService.updateBrand(brand));
    }
}
