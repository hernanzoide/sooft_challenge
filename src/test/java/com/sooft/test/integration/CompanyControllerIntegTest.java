package com.sooft.test.integration;

import com.sooft.test.dto.CompanyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "/companies";
    }

    @Test
    public void testAddCompany() {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .cuit("20-12345678-9")
                .companyName("Company A")
                .subscriptionDate(LocalDate.now()).build();

        ResponseEntity<CompanyDTO> response = restTemplate.postForEntity(baseUrl, companyDTO, CompanyDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCompanyName()).isEqualTo("Company A");
        assertThat(response.getBody().getCuit()).isEqualTo("20-12345678-9");
        assertThat(response.getBody().getSubscriptionDate()).isNotNull();
    }
}
