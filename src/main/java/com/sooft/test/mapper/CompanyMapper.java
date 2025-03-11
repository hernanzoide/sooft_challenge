package com.sooft.test.mapper;

import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.model.Company;

public class CompanyMapper {

    public static CompanyDTO toDto(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .subscriptionDate(company.getSubscriptionDate())
                .cuit(company.getCuit())
                .build();
    }
}
