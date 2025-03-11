package com.sooft.test.service;

import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.model.Company;
import com.sooft.test.repository.CompanyRepository;
import com.sooft.test.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    public void setUp() {
        company = Company.builder()
                .companyName("Company A")
                .cuit("20-12345678-9")
                .subscriptionDate(LocalDate.now())
                .build();
    }

    @Test
    public void testSubscribeCompany() {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .companyName("Company A")
                .cuit("20-12345678-9")
                .build();

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        CompanyDTO result = companyService.subscribeCompany(companyDTO);

        assertNotNull(result);
        assertEquals("Company A", result.getCompanyName());
        assertEquals("20-12345678-9", result.getCuit());

        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    public void testGetCompaniesWithTransfersFromLastMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfLastMonth = today.minusMonths(1);

        List<Long> companyIds = Arrays.asList(1L, 2L);
        when(transferRepository.findDistinctCompanyIdsByDateBetween(startOfLastMonth, today)).thenReturn(companyIds);

        Company company1 = Company.builder()
                .id(1L)
                .companyName("Company1")
                .build();
        Company company2 = Company.builder()
                .id(2L).
                companyName("Company2")
                .build();
        when(companyRepository.findAllByIdIn(companyIds)).thenReturn(Arrays.asList(company1, company2));

        List<CompanyDTO> result = companyService.getCompaniesWithTransfersFromLastMonth();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Company1", result.get(0).getCompanyName());
        assertEquals("Company2", result.get(1).getCompanyName());

        verify(transferRepository, times(1)).findDistinctCompanyIdsByDateBetween(startOfLastMonth, today);
    }

    @Test
    public void testGetCompanies() {
        Company company1 = Company.builder()
                .id(1L)
                .companyName("Company1")
                .cuit("20-12345678-9")
                .subscriptionDate(LocalDate.now())
                .build();
        Company company2 = Company.builder()
                .id(2L)
                .companyName("Company2")
                .cuit("20-87654321-0")
                .subscriptionDate(LocalDate.now())
                .build();

        when(companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2));

        List<CompanyDTO> result = companyService.getCompanies();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Company1", result.get(0).getCompanyName());
        assertEquals("Company2", result.get(1).getCompanyName());

        verify(companyRepository, times(1)).findAll();
    }
}
