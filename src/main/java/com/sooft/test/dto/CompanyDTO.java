package com.sooft.test.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CompanyDTO {

    private Long id;
    private String cuit;
    private String companyName;
    private LocalDate subscriptionDate;
}
