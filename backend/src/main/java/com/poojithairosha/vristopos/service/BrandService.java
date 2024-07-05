package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.model.product.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    List<Brand> getBrands();

    Page<Brand> searchBrands(String name, int page, int size);

    ClientResponse createBrand(Brand brand);

    Brand getBrand(Long id);

    ClientResponse updateBrand(Brand brand);
}
