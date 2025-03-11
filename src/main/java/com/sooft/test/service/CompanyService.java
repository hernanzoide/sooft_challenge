package com.sooft.test.service;

import com.sooft.test.dto.CompanyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {

    CompanyDTO subscribeCompany(CompanyDTO companyDto);

    List<CompanyDTO> getCompaniesWithTransfersFromLastMonth();

    List<CompanyDTO> getCompanies();
}
