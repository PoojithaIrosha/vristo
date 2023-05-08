package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.CompanyDTO;
import com.poojithairosha.vristopos.model.supplier.Company;
import com.poojithairosha.vristopos.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Page<CompanyDTO> searchCompanies(int page, int size, String text) {
        PageRequest pr = PageRequest.of(page, size);
        return companyRepository.findByEmailContainingOrNameContainingOrMobileContaining(text, text, text, pr).map(c -> new CompanyDTO(c.getId(), c.getName(), c.getMobile(), c.getEmail(), c.getAddress()));
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream().map(c -> new CompanyDTO(c.getId(), c.getName(), c.getMobile(), c.getEmail(), c.getAddress())).toList();
    }

    public ClientResponse registerCompany(CompanyDTO companyDTO) {
        if (companyRepository.existsByEmailOrNameOrMobile(companyDTO.email(), companyDTO.name(), companyDTO.mobile())) {
            throw new RuntimeException("Company already exists");
        }
        companyRepository.save(Company.builder().name(companyDTO.name()).mobile(companyDTO.mobile()).address(companyDTO.address()).email(companyDTO.email()).build());
        log.info("Company registered successfully with email: {}", companyDTO.email());
        return new ClientResponse(true, "Company registered successfully");
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        return new CompanyDTO(company.getId(), company.getName(), company.getMobile(), company.getEmail(), company.getAddress());
    }

    public ClientResponse updateCompany(CompanyDTO companyDTO) {
        if (!companyRepository.existsById(companyDTO.id()))
            throw new RuntimeException("Company not found");

        companyRepository.save(Company.builder().id(companyDTO.id()).name(companyDTO.name()).mobile(companyDTO.mobile()).address(companyDTO.address()).email(companyDTO.email()).build());
        log.info("Company updated successfully with email: {}", companyDTO.email());
        return new ClientResponse(true, "Company updated successfully");
    }
}
