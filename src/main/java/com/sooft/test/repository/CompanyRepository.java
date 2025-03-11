package com.sooft.test.repository;

import com.sooft.test.model.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository {

    Company save(Company company);

    List<Company> findAll();

    List<Company> findAllByIdIn(List<Long> companyIds);

    Optional<Company> findById(Long companyId);
}
