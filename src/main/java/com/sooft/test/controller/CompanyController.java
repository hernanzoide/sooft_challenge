package com.sooft.test.controller;

import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/transfer")
    public ResponseEntity<List<CompanyDTO>> getCompaniesWithTransfer() {
        List<CompanyDTO> companies = companyService.getCompaniesWithTransfersFromLastMonth();
        return companies.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(companies);
    }

    @GetMapping("/subscribed")
    public ResponseEntity<List<CompanyDTO>> getSubscribed() {
        List<CompanyDTO> companies = companyService.getCompanies();
        return companies.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> addCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompanyDto = companyService.subscribeCompany(companyDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCompanyDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCompanyDto);
    }

}
