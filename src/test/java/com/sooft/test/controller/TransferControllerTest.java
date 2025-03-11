package com.sooft.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooft.test.dto.TransferDTO;
import com.sooft.test.exception.CompanyNotFoundException;
import com.sooft.test.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.net.URI;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    public void testAddTransferSuccess() throws Exception {
        TransferDTO transferDTO = TransferDTO.builder()
                .amount(BigDecimal.valueOf(1000.0))
                .debitAccount("1234")
                .creditAccount("5678")
                .companyId(1L)
                .build();

        TransferDTO createdTransferDTO = TransferDTO.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(1000.0))
                .debitAccount("1234")
                .creditAccount("5678")
                .companyId(1L)
                .build();

        when(transferService.addTransfer(any(TransferDTO.class))).thenReturn(createdTransferDTO);

        URI location = URI.create("http://localhost/transfers/1");

        mockMvc.perform(post("/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transferDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", location.toString()))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.debitAccount").value("1234"))
                .andExpect(jsonPath("$.creditAccount").value("5678"))
                .andExpect(jsonPath("$.companyId").value(1L));
    }

    @Test
    public void testAddTransferCompanyNotFound() throws Exception {
        TransferDTO transferDTO = TransferDTO.builder()
                .amount(BigDecimal.valueOf(1000.0))
                .debitAccount("1234")
                .creditAccount("5678")
                .companyId(1L)
                .build();

        when(transferService.addTransfer(any(TransferDTO.class)))
                .thenThrow(new CompanyNotFoundException(1L));

        mockMvc.perform(post("/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transferDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Company not found for id: 1"));
    }
}
