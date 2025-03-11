package com.sooft.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String debitAccount;
    private String creditAccount;
    private LocalDate date;
    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

}
