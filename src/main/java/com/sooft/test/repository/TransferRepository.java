package com.sooft.test.repository;

import com.sooft.test.model.Transfer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository {

    Transfer save(Transfer transfer);

    List<Long> findDistinctCompanyIdsByDateBetween(LocalDate startDate, LocalDate endDate);

}
