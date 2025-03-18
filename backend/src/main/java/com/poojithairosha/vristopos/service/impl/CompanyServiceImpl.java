package com.poojithairosha.vristopos.service.impl;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.CompanyDTO;
import com.poojithairosha.vristopos.model.supplier.Company;
import com.poojithairosha.vristopos.repository.CompanyRepository;
import com.poojithairosha.vristopos.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyDTO> searchCompanies(int page, int size, String text) {
        log.info("Searching company by {}", text);
        PageRequest pr = PageRequest.of(page, size);
        Page<CompanyDTO> result = companyRepository.findByEmailContainingOrNameContainingOrMobileContaining(text, text, text, pr).map(c -> new CompanyDTO(c.getId(), c.getName(), c.getMobile(), c.getEmail(), c.getAddress()));
        log.info("Search company result: {}", result);
        return result;
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        log.info("Start executing getAllCompanies");
        List<CompanyDTO> list = companyRepository.findAll().stream().map(c -> new CompanyDTO(c.getId(), c.getName(), c.getMobile(), c.getEmail(), c.getAddress())).toList();
        log.info("End executing getAllCompanies and found {} companies", list.size());
        return list;
    }

    @Override
    public ClientResponse registerCompany(CompanyDTO companyDTO) {
        log.info("Start executing registerCompany");
        if (companyRepository.existsByEmailOrNameOrMobile(companyDTO.email(), companyDTO.name(), companyDTO.mobile())) {
            throw new RuntimeException("Company already exists");
        }
        companyRepository.save(Company.builder().name(companyDTO.name()).mobile(companyDTO.mobile()).address(companyDTO.address()).email(companyDTO.email()).build());
        log.info("Company registered successfully with email: {}", companyDTO.email());
        return new ClientResponse(true, "Company registered successfully");
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        log.info("Start executing getCompanyById");
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        log.info("Company found: {}", company);
        return new CompanyDTO(company.getId(), company.getName(), company.getMobile(), company.getEmail(), company.getAddress());
    }

    @Override
    public ClientResponse updateCompany(CompanyDTO companyDTO) {
        log.info("Start executing updateCompany");
        if (!companyRepository.existsById(companyDTO.id()))
            throw new RuntimeException("Company not found");

        companyRepository.save(Company.builder().id(companyDTO.id()).name(companyDTO.name()).mobile(companyDTO.mobile()).address(companyDTO.address()).email(companyDTO.email()).build());
        log.info("Company updated successfully with email: {}", companyDTO.email());
        return new ClientResponse(true, "Company updated successfully");
    }
}
