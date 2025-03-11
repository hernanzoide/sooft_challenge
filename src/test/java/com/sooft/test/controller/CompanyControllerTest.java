package com.sooft.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooft.test.dto.CompanyDTO;
import com.sooft.test.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    public void testGetCompaniesWithTransferSuccess() throws Exception {
        CompanyDTO companyDTO1 = CompanyDTO.builder()
                .id(1L)
                .companyName("Company A")
                .cuit("1234567890")
                .build();
        CompanyDTO companyDTO2 = CompanyDTO.builder()
                .id(2L)
                .companyName("Company B")
                .cuit("0987654321")
                .build();

        List<CompanyDTO> companies = List.of(companyDTO1, companyDTO2);

        when(companyService.getCompaniesWithTransfersFromLastMonth()).thenReturn(companies);

        mockMvc.perform(get("/companies/transfer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Company A"))
                .andExpect(jsonPath("$[1].companyName").value("Company B"));
    }

    @Test
    public void testGetCompaniesWithTransferNotFound() throws Exception {
        when(companyService.getCompaniesWithTransfersFromLastMonth()).thenReturn(List.of());

        mockMvc.perform(get("/companies/transfer"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSubscribedSuccess() throws Exception {
        CompanyDTO companyDTO1 = CompanyDTO.builder()
                .id(1L)
                .companyName("Company A")
                .cuit("1234567890")
                .build();
        CompanyDTO companyDTO2 = CompanyDTO.builder()
                .id(2L)
                .companyName("Company B")
                .cuit("0987654321")
                .build();
        List<CompanyDTO> companies = List.of(companyDTO1, companyDTO2);

        when(companyService.getCompanies()).thenReturn(companies);

        mockMvc.perform(get("/companies/subscribed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Company A"))
                .andExpect(jsonPath("$[1].companyName").value("Company B"));
    }

    @Test
    public void testGetSubscribedNotFound() throws Exception {
        when(companyService.getCompanies()).thenReturn(List.of());

        mockMvc.perform(get("/companies/subscribed"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddCompanySuccess() throws Exception {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .companyName("New Company")
                .cuit("1234567890")
                .build();
        CompanyDTO createdCompanyDTO = CompanyDTO.builder()
                .id(1L)
                .companyName("New Company")
                .cuit("1234567890")
                .build();

        when(companyService.subscribeCompany(any(CompanyDTO.class))).thenReturn(createdCompanyDTO);

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("New Company"))
                .andExpect(jsonPath("$.id").value(1L));
    }

}
