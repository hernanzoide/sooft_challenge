package com.sooft.test.mapper;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.model.Company;
import com.sooft.test.model.Transfer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransferMapperTest {

    @Test
    public void testToDto() {
        Company company = Company.builder()
                .id(1L)
                .companyName("Company A")
                .subscriptionDate(LocalDate.of(2023, 5, 1))
                .cuit("20-12345678-9")
                .build();

        Transfer transfer = Transfer.builder()
                .id(100L)
                .amount(BigDecimal.valueOf(5000.0))
                .debitAccount("12345")
                .creditAccount("67890")
                .company(company)
                .build();

        TransferDTO transferDTO = TransferMapper.toDto(transfer);

        assertNotNull(transferDTO);
        assertEquals(transfer.getId(), transferDTO.getId());
        assertEquals(transfer.getAmount(), transferDTO.getAmount());
        assertEquals(transfer.getCompany().getId(), transferDTO.getCompanyId());
        assertEquals(transfer.getCreditAccount(), transferDTO.getCreditAccount());
        assertEquals(transfer.getDebitAccount(), transferDTO.getDebitAccount());
    }
}
