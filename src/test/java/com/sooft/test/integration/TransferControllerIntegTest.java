package com.sooft.test.integration;
import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.dto.TransferDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    private CompanyDTO companyDto;

    @BeforeEach
    public void setUp() {

        CompanyDTO companyDTO = CompanyDTO.builder()
                .cuit("20-12345678-9")
                .companyName("Company A")
                .subscriptionDate(LocalDate.now()).build();

        ResponseEntity<CompanyDTO> companyDto = restTemplate.postForEntity("/companies", companyDTO, CompanyDTO.class);

        this.companyDto = companyDto.getBody();

        baseUrl = "/transfers";
    }

    @Test
    public void testAddTransfer() {
        TransferDTO transferDTO = TransferDTO.builder()
                .amount(BigDecimal.valueOf(500.0))
                .debitAccount("123456789")
                .creditAccount("987654321")
                .date(LocalDate.now())
                .companyId(companyDto.getId())
                .build();

        ResponseEntity<TransferDTO> response = restTemplate.postForEntity(baseUrl, transferDTO, TransferDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAmount()).isEqualTo(BigDecimal.valueOf(500.0));
        assertThat(response.getBody().getDebitAccount()).isEqualTo("123456789");
        assertThat(response.getBody().getCreditAccount()).isEqualTo("987654321");
    }
}
