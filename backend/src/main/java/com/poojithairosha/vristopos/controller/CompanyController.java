package com.poojithairosha.vristopos.controller;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.CompanyDTO;
import com.poojithairosha.vristopos.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDTO>> searchCompanies(@RequestParam int page, @RequestParam int size, @RequestParam String text) {
        return ResponseEntity.ok(companyService.searchCompanies(page, size, text));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.registerCompany(companyDTO));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponse> updateCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.updateCompany(companyDTO));
    }
}
