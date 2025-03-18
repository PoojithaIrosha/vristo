package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.SupplierDTO;
import com.poojithairosha.vristopos.model.supplier.Supplier;
import com.poojithairosha.vristopos.repository.CompanyRepository;
import com.poojithairosha.vristopos.repository.SupplierRepository;
import com.poojithairosha.vristopos.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Page<Supplier> searchSuppliers(int page, int size, String text) {
        log.info("Start searching suppliers with text {}", text);
        PageRequest pr = PageRequest.of(page, size);
        Page<Supplier> result = supplierRepository.findByEmailContainingOrNameContainingOrMobileContaining(text, text, text, pr);
        log.info("End searching suppliers with text {}, found {} of suppliers", text, result.getNumberOfElements());
        return result;
    }

    @Override
    public ClientResponse registerSupplier(SupplierDTO supplierDTO) {
        log.info("Start registering supplier");
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

    @Override
    public Supplier getSupplier(Long id) {
        log.info("Finding supplier by id: {}", id);
        Supplier result = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
        log.info("Supplier found: {}", result);
        return result;
    }

    @Override
    public ClientResponse updateSupplier(Supplier supplier) {
        log.info("Start updating supplier");
        if (!supplierRepository.existsById(supplier.getId()))
            throw new RuntimeException("Supplier not found");

        if (!companyRepository.existsById(supplier.getCompany().getId()))
            throw new RuntimeException("Company not found");

        supplierRepository.save(supplier);
        log.info("Supplier updated successfully. Supplier #{}", supplier.getId());
        return new ClientResponse(true, "Supplier updated successfully");
    }
}
