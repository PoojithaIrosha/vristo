package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ProductDTO;
import com.poojithairosha.vristopos.model.product.Brand;
import com.poojithairosha.vristopos.model.product.Product;
import com.poojithairosha.vristopos.model.product.Unit;
import com.poojithairosha.vristopos.repository.BrandRepository;
import com.poojithairosha.vristopos.repository.ProductRepository;
import com.poojithairosha.vristopos.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final BrandRepository brandRepository;

    public Page<Product> searchProducts(int page, int size, String text) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findByNameContaining(text, pageRequest);
    }

    public ClientResponse registerProduct(ProductDTO productDTO) {
        if (productRepository.existsByNameAndBrand_Id(productDTO.name(), productDTO.brand()))
            throw new RuntimeException("Product already exists");

        Unit unit = unitRepository.findById(productDTO.unit()).orElseThrow(() -> new RuntimeException("Product Unit Not Found!"));
        Brand brand = brandRepository.findById(productDTO.brand()).orElseThrow(() -> new RuntimeException("Product Brand Not Found"));
        productRepository.save(Product.builder().name(productDTO.name()).unit(unit).brand(brand).build());
        log.info("Product registered successfully with name: {}", productDTO.name());
        return new ClientResponse(true, "Product registered successfully");
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    public ClientResponse updateProduct(ProductDTO productDTO) {
        if (!productRepository.existsById(productDTO.id()))
            throw new RuntimeException("Product not found");

        Unit unit = unitRepository.findById(productDTO.unit()).orElseThrow(() -> new RuntimeException("Product Unit Not Found!"));
        Brand brand = brandRepository.findById(productDTO.brand()).orElseThrow(() -> new RuntimeException("Product Brand Not Found"));
        productRepository.save(Product.builder().id(productDTO.id()).name(productDTO.name()).unit(unit).brand(brand).build());
        log.info("Product updated successfully with name: {}", productDTO.name());
        return new ClientResponse(true, "Product updated successfully");
    }
}
