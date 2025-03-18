package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Category;
import com.poojithairosha.vristopos.repository.CategoryRepository;
import com.poojithairosha.vristopos.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        log.info("Start executing getCategories");
        List<Category> list = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        log.info("Finished executing getCategories");
        return list;
    }

    @Override
    public Page<Category> searchCategories(String text, int page, int size) {
        log.info("Start executing searchCategories");
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Category> categories = categoryRepository.findByNameContaining(text, pageRequest);
        log.info("Finished executing searchCategories with name: {}", text);
        return categories;
    }

    @Override
    public ClientResponse createCategory(Category category) {
        log.info("Start executing createCategory");
        if (categoryRepository.existsByName(category.getName()))
            throw new RuntimeException("Category already exists");

        Category saved = categoryRepository.save(category);
        System.out.println(saved);
        log.info("Category saved successfully with id: {}", category.getId());
        return new ClientResponse(true, "Category created successfully");
    }

    @Override
    public ClientResponse updateCategory(Category category) {
        log.info("Start executing updateCategory");
        if (!categoryRepository.existsById(category.getId()))
            throw new RuntimeException("Category does not exist");

        categoryRepository.save(category);
        log.info("Category updated successfully with id: {}", category.getId());
        return new ClientResponse(true, "Category updated successfully");
    }

    @Override
    public Category getCategory(Long id) {
        log.info("Start executing getCategory");
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category does not exist"));
        log.info("Finished executing getCategory, found category: {}", category);
        return category;
    }
}
