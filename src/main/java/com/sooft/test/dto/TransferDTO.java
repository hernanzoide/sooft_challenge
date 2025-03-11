package com.sooft.test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransferDTO {

    private Long id;
    private BigDecimal amount;
    private String debitAccount;
    private String creditAccount;
    private LocalDate date;
    private Long companyId;

}
