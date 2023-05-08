package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.SupplierDTO;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import com.poojithairosha.vristopos.repository.CompanyRepository;
import com.poojithairosha.vristopos.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;

    public Page<Supplier> searchSuppliers(int page, int size, String text) {
        PageRequest pr = PageRequest.of(page, size);
        return supplierRepository.findByEmailContainingOrNameContainingOrMobileContaining(text, text, text, pr);
    }

    public ClientResponse registerSupplier(SupplierDTO supplierDTO) {
        if (supplierRepository.existsByEmailAndMobile(supplierDTO.email(), supplierDTO.mobile()))
            throw new RuntimeException("Email or Mobile already exists");

        if (!companyRepository.existsById(supplierDTO.company()))
            throw new RuntimeException("Company not found");

        Supplier supplier = Supplier.builder()
                .email(supplierDTO.email())
                .name(supplierDTO.name())
                .mobile(supplierDTO.mobile())
                .company(companyRepository.findById(supplierDTO.company()).orElseThrow(() -> new RuntimeException("Company Not Found"))).build();
        supplierRepository.save(supplier);
        log.info("Supplier registered successfully. Supplier #{}", supplier.getId());
        return new ClientResponse(true, "Supplier registered successfully");
    }

    public Supplier getSupplier(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public ClientResponse updateSupplier(Supplier supplier) {
        if (!supplierRepository.existsById(supplier.getId()))
            throw new RuntimeException("Supplier not found");

        if (!companyRepository.existsById(supplier.getCompany().getId()))
            throw new RuntimeException("Company not found");

        supplierRepository.save(supplier);
        log.info("Supplier updated successfully. Supplier #{}", supplier.getId());
        return new ClientResponse(true, "Supplier updated successfully");
    }
}
