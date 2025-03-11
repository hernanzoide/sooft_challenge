package com.sooft.test.mapper;

import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.model.Company;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyMapperTest {

    @Test
    public void testToDto() {
        Company company = Company.builder()
                .id(1L)
                .companyName("Company A")
                .subscriptionDate(LocalDate.of(2023, 5, 1))
                .cuit("20-12345678-9")
                .build();

        CompanyDTO companyDTO = CompanyMapper.toDto(company);

        assertNotNull(companyDTO);
        assertEquals(company.getId(), companyDTO.getId());
        assertEquals(company.getCompanyName(), companyDTO.getCompanyName());
        assertEquals(company.getSubscriptionDate(), companyDTO.getSubscriptionDate());
        assertEquals(company.getCuit(), companyDTO.getCuit());
    }
}
