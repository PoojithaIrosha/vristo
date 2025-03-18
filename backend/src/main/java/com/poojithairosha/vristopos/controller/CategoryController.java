package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Category;
import com.poojithairosha.vristopos.service.CategoryService;
import com.poojithairosha.vristopos.service.impl.CategoryServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_CATEGORIES)
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        log.info("Start execute getCategories");
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<Category>> searchCategories(@RequestParam String text, @RequestParam int page, @RequestParam int size) {
        log.info("Start execute searchCategories");
        return ResponseEntity.ok(categoryService.searchCategories(text, page, size));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        log.info("Start execute getCategory");
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> createCategory(@RequestBody Category category) {
        log.info("Start execute createCategory");
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateCategory(@RequestBody Category category) {
        log.info("Start execute updateCategory");
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }
}

