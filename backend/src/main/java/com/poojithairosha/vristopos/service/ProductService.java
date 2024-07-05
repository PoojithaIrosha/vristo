package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ProductDTO;
import com.poojithairosha.vristopos.model.product.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> searchProducts(int page, int size, String text);

    ClientResponse registerProduct(ProductDTO productDTO);

    Product getProduct(Long id);

    ClientResponse updateProduct(ProductDTO productDTO);

}
