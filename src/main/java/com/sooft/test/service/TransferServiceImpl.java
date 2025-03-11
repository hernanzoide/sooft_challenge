package com.sooft.test.service;

import com.sooft.test.dto.TransferDTO;
import com.sooft.test.exception.CompanyNotFoundException;
import com.sooft.test.model.Company;
import com.sooft.test.model.Transfer;
import com.sooft.test.repository.CompanyRepository;
import com.sooft.test.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.sooft.test.mapper.TransferMapper.toDto;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    CompanyRepository companyRepository;

    public TransferDTO addTransfer(TransferDTO transferDto) throws CompanyNotFoundException {
        return companyRepository.findById(transferDto.getCompanyId()).map(c -> toDto(transferRepository.save(Transfer.builder()
                .date(LocalDate.now())
                .amount(transferDto.getAmount())
                .debitAccount(transferDto.getDebitAccount())
                .creditAccount(transferDto.getCreditAccount())
                .company(c)
                .build())))
                .orElseThrow(() -> new CompanyNotFoundException(transferDto.getCompanyId()));
    }
}
