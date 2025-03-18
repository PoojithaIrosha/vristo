package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.repository.BrandRepository;
import com.poojithairosha.vristopos.repository.CategoryRepository;
import com.poojithairosha.vristopos.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Brand> getBrands() {
        log.info("Start executing getBrands");
        List<Brand> brands = brandRepository.findAll();
        log.info("Finished executing getBrands. Total: {}", brands.size());
        return brands;
    }

    @Override
    public Page<Brand> searchBrands(String name, int page, int size) {
        log.info("Start executing searchBrands");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Brand> brands = brandRepository.findByNameContaining(name, pageRequest);
        log.info("Finished executing searchBrands. Search: {}, Total: {}", name, brands.getTotalElements());
        return brands;
    }

    @Override
    public ClientResponse createBrand(Brand brand) {
        log.info("Start executing createBrand");
        if (brandRepository.existsByName(brand.getName())) {
            log.error("Brand already exists with name: {}", brand.getName());
            throw new RuntimeException("Brand already exists");
        }

        brandRepository.save(brand);
        log.info("New brand saved successfully with name: {}", brand.getName());
        return new ClientResponse(true, "Brand created successfully");
    }

    @Override
    public Brand getBrand(Long id) {
        log.info("Start executing getBrand");
        return brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public ClientResponse updateBrand(Brand brand) {
        log.info("Start executing updateBrand");
        if (!brandRepository.existsById(brand.getId()))
            throw new RuntimeException("Brand not found");

        if (!categoryRepository.existsById(brand.getCategory().getId()))
            throw new RuntimeException("Category not found");

        brandRepository.save(brand);
        log.info("Brand updated successfully with id: {}", brand.getId());
        return new ClientResponse(true, "Brand updated successfully");
    }
}
