package com.sooft.test.mapper;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.model.Transfer;
import com.sooft.test.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransferMapper {

    @Autowired
    CompanyRepository companyRepository;

    public static TransferDTO toDto(Transfer transfer) {
        return TransferDTO.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .companyId(transfer.getCompany().getId())
                .creditAccount(transfer.getCreditAccount())
                .debitAccount(transfer.getDebitAccount())
                .build();
    }
}
