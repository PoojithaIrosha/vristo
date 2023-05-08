package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Category;
import com.poojithairosha.vristopos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Category>> searchCategories(@RequestParam String text, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(categoryService.searchCategories(text, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }
}

