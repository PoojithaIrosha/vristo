package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.repository.BrandRepository;
import com.poojithairosha.vristopos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Page<Brand> searchBrands(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return brandRepository.findByNameContaining(name, pageRequest);
    }

    public ClientResponse createBrand(Brand brand) {
        if (brandRepository.existsByName(brand.getName()))
            throw new RuntimeException("Brand already exists");

        brandRepository.save(brand);
        log.info("Brand created successfully with id: " + brand.getId());
        return new ClientResponse(true, "Brand created successfully");
    }

    public Brand getBrand(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    public ClientResponse updateBrand(Brand brand) {
        if (!brandRepository.existsById(brand.getId()))
            throw new RuntimeException("Brand not found");

        if (!categoryRepository.existsById(brand.getCategory().getId()))
            throw new RuntimeException("Category not found");

        brandRepository.save(brand);
        log.info("Brand updated successfully with id: " + brand.getId());
        return new ClientResponse(true, "Brand updated successfully");
    }
}
