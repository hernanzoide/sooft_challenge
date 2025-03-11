package com.sooft.test.repository;

import com.sooft.test.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransferRepositoryImpl extends TransferRepository, JpaRepository<Transfer, Long> {

    @Query("SELECT DISTINCT t.company.id FROM Transfer t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Long> findDistinctCompanyIdsByDateBetween(LocalDate startDate, LocalDate endDate);

}
