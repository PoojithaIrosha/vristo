package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.CompanyDTO;
import com.poojithairosha.vristopos.service.CompanyService;
import com.poojithairosha.vristopos.service.impl.CompanyServiceImpl;
import com.poojithairosha.vristopos.util.UriProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriProperties.URI_COMPANIES)
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        log.info("Start execute getAllCompanies");
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping(UriProperties.URI_SEARCH)
    public ResponseEntity<Page<CompanyDTO>> searchCompanies(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        log.info("Start execute searchCompanies");
        return ResponseEntity.ok(companyService.searchCompanies(page, size, text));
    }

    @GetMapping(UriProperties.URI_FIND_BY_ID)
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        log.info("Start execute getCompanyById");
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        log.info("Start execute registerCompany");
        return ResponseEntity.ok(companyService.registerCompany(companyDTO));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        log.info("Start execute updateCompany");
        return ResponseEntity.ok(companyService.updateCompany(companyDTO));
    }
}
