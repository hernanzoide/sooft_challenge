package com.sooft.test.service;

import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.mapper.CompanyMapper;
import com.sooft.test.model.Company;
import com.sooft.test.repository.CompanyRepository;
import com.sooft.test.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.sooft.test.mapper.CompanyMapper.toDto;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    TransferRepository transferRepository;

    public CompanyDTO subscribeCompany(CompanyDTO companyDto) {
        return toDto(companyRepository.save(Company.builder()
                .companyName(companyDto.getCompanyName())
                .subscriptionDate(LocalDate.now())
                .cuit(companyDto.getCuit())
                .build()));
    }

    @Override
    public List<CompanyDTO> getCompaniesWithTransfersFromLastMonth() {
        LocalDate today = LocalDate.now();
        //let's assume last month is one month less than today
        LocalDate startOfLastMonth = today.minusMonths(1);

        List<Long> companyIds = transferRepository.findDistinctCompanyIdsByDateBetween(startOfLastMonth, today);

        return companyRepository.findAllByIdIn(companyIds).stream()
                .map(CompanyMapper::toDto)
                .toList();
    }

    @Override
    public List<CompanyDTO> getCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::toDto)
                .toList();
    }


}
