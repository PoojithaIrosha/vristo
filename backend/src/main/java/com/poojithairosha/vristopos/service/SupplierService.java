package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.SupplierDTO;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import org.springframework.data.domain.Page;

public interface SupplierService {

    Page<Supplier> searchSuppliers(int page, int size, String text);

    ClientResponse registerSupplier(SupplierDTO supplierDTO);

    Supplier getSupplier(Long id);

    ClientResponse updateSupplier(Supplier supplier);

}
