package com.sooft.test.service;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.exception.CompanyNotFoundException;
import com.sooft.test.model.Company;
import com.sooft.test.model.Transfer;
import com.sooft.test.repository.CompanyRepository;
import com.sooft.test.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private TransferDTO transferDTO;
    private Company company;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        transferDTO = TransferDTO.builder()
                .companyId(1L)
                .amount(BigDecimal.valueOf(1000.0))
                .debitAccount("12345")
                .creditAccount("67890")
                .build();

        company = Company.builder()
                .id(1L)
                .companyName("Company A")
                .build();
    }

    @Test
    public void testAddTransferSuccess() throws CompanyNotFoundException {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Transfer savedTransfer = Transfer.builder()
                .date(LocalDate.now())
                .amount(BigDecimal.valueOf(1000.0))
                .debitAccount("12345")
                .creditAccount("67890")
                .company(company)
                .build();
        when(transferRepository.save(any(Transfer.class))).thenReturn(savedTransfer);

        TransferDTO result = transferService.addTransfer(transferDTO);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1000.0), result.getAmount());
        assertEquals("12345", result.getDebitAccount());
        assertEquals("67890", result.getCreditAccount());
        assertEquals(company.getId(), result.getCompanyId());

        verify(companyRepository, times(1)).findById(1L);
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    public void testAddTransferCompanyNotFound() throws CompanyNotFoundException {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        CompanyNotFoundException thrown = assertThrows(CompanyNotFoundException.class, () -> {
            transferService.addTransfer(transferDTO);
        });

        assertEquals("Company not found for id: 1", thrown.getMessage());
        verify(transferRepository, times(0)).save(any(Transfer.class));
    }

}
