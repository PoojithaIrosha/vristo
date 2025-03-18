package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ProductDTO;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.model.product.Unit;
import com.poojithairosha.vristopos.repository.BrandRepository;
import com.poojithairosha.vristopos.repository.ProductRepository;
import com.poojithairosha.vristopos.repository.UnitRepository;
import com.poojithairosha.vristopos.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final BrandRepository brandRepository;

    @Override
    public Page<Product> searchProducts(int page, int size, String text) {
        log.info("Start executing search products");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByNameContaining(text, pageRequest);
        log.info("Products found : {}", products.getTotalElements());
        return products;
    }

    @Override
    public ClientResponse registerProduct(ProductDTO productDTO) {
        log.info("Start executing register product");
        if (productRepository.existsByNameAndBrand_Id(productDTO.name(), productDTO.brand())) {
            log.error("Product already exists with name {}", productDTO.name());
            throw new RuntimeException("Product already exists");
        }

        Unit unit = unitRepository.findById(productDTO.unit()).orElseThrow(() -> new RuntimeException("Product Unit Not Found!"));
        Brand brand = brandRepository.findById(productDTO.brand()).orElseThrow(() -> new RuntimeException("Product Brand Not Found"));
        productRepository.save(Product.builder().name(productDTO.name()).unit(unit).brand(brand).build());
        log.info("Product registered successfully with name: {}", productDTO.name());
        return new ClientResponse(true, "Product registered successfully");
    }

    @Override
    public Product getProduct(Long id) {
        log.info("Start executing getProduct");
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public ClientResponse updateProduct(ProductDTO productDTO) {
        log.info("Start executing update product");
        if (!productRepository.existsById(productDTO.id()))
            throw new RuntimeException("Product not found");

        Unit unit = unitRepository.findById(productDTO.unit()).orElseThrow(() -> new RuntimeException("Product Unit Not Found!"));
        Brand brand = brandRepository.findById(productDTO.brand()).orElseThrow(() -> new RuntimeException("Product Brand Not Found"));
        productRepository.save(Product.builder().id(productDTO.id()).name(productDTO.name()).unit(unit).brand(brand).build());
        log.info("Product updated successfully with name: {}", productDTO.name());
        return new ClientResponse(true, "Product updated successfully");
    }
}
