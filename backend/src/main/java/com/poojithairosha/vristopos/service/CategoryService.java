package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Category;
import com.poojithairosha.vristopos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Page<Category> searchCategories(String text, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return categoryRepository.findByNameContaining(text, pageRequest);
    }

    public ClientResponse createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName()))
            throw new RuntimeException("Category already exists");

        Category saved = categoryRepository.save(category);
        System.out.println(saved);
        log.info("Category created successfully with id: {}", category.getId());
        return new ClientResponse(true, "Category created successfully");
    }

    public ClientResponse updateCategory(Category category) {
        if (!categoryRepository.existsById(category.getId()))
            throw new RuntimeException("Category does not exist");

        categoryRepository.save(category);
        log.info("Category updated successfully with id: {}", category.getId());
        return new ClientResponse(true, "Category updated successfully");
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category does not exist"));
    }
}
