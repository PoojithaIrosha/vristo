package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.CompanyDTO;
import com.poojithairosha.vristopos.model.supplier.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CompanyService {

    Page<CompanyDTO> searchCompanies(int page, int size, String text);

    List<CompanyDTO> getAllCompanies();

    ClientResponse registerCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long id);

    ClientResponse updateCompany(CompanyDTO companyDTO);

}
