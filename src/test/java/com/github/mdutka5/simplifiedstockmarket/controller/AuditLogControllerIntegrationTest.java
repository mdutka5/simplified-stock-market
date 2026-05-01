package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.BaseIntegrationTest;
import com.github.mdutka5.simplifiedstockmarket.repository.AuditLogRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.BankStockRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuditLogControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    BankStockRepository bankStockRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletStockRepository walletStockRepository;

    @BeforeEach
    void setUp() throws Exception {
        auditLogRepository.deleteAll();
        walletStockRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockRepository.deleteAll();

        mockMvc.perform(post("/stocks")
                .contentType(APPLICATION_JSON)
                .content("{\"stocks\": [{\"name\": \"AAPL\", \"quantity\": 100}]}"));
    }

    @Test
    void shouldReturnEmptyLogOnStart() throws Exception {
        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log").isArray())
                .andExpect(jsonPath("$.log").isEmpty());
    }

    @Test
    void shouldLogSuccessfulBuy() throws Exception {
        mockMvc.perform(post("/wallets/john/stocks/AAPL")
                        .contentType(APPLICATION_JSON)
                        .content("{\"type\": \"buy\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log[0].type").value("buy"))
                .andExpect(jsonPath("$.log[0].walletId").value("john"))
                .andExpect(jsonPath("$.log[0].stockName").value("AAPL"));
    }

    @Test
    void shouldNotLogFailedOperation() throws Exception {
        mockMvc.perform(post("/wallets/john/stocks/INVALID")
                .contentType(APPLICATION_JSON)
                .content("{\"type\": \"buy\"}"));

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log").isEmpty());
    }
}