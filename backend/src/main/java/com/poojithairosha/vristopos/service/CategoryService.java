package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Page<Category> searchCategories(String text, int page, int size);

    ClientResponse createCategory(Category category);

    ClientResponse updateCategory(Category category);

    Category getCategory(Long id);

}
