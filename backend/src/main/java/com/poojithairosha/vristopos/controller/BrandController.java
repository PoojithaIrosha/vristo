package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Brand>> searchBrands(@RequestParam String text, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(brandService.searchBrands(text, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrand(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> createBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.createBrand(brand));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.updateBrand(brand));
    }
}
