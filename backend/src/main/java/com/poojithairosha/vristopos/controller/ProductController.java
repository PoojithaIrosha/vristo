package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ProductDTO;
import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.service.ProductService;
import com.poojithairosha.vristopos.service.impl.ProductServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriProperties.URI_PRODUCT)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        log.info("Start execute searchProducts");
        return ResponseEntity.ok(productService.searchProducts(page, size, text));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        log.info("Start execute getProduct");
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> registerProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Start execute registerProduct");
        return ResponseEntity.ok(this.productService.registerProduct(productDTO));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Start execute updateProduct");
        return ResponseEntity.ok(this.productService.updateProduct(productDTO));
    }

}
