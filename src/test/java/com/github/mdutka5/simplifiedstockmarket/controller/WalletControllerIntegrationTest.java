package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.BaseIntegrationTest;
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

public class WalletControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BankStockRepository bankStockRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletStockRepository walletStockRepository;

    @BeforeEach
    void setUp() throws Exception {
        walletStockRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockRepository.deleteAll();

        mockMvc.perform(post("/stocks")
                .contentType(APPLICATION_JSON)
                .content("{\"stocks\": [{\"name\": \"AAPL\", \"quantity\": 100}]}"));
    }

    @Test
    void shouldReturn404WhenWalletDoesNotExist() throws Exception {
        mockMvc.perform(get("/wallets/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenBuyingNonExistentStock() throws Exception {
        mockMvc.perform(post("/wallets/john/stocks/INVALID")
                        .contentType(APPLICATION_JSON)
                        .content("{\"type\": \"buy\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenBankIsEmpty() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType(APPLICATION_JSON)
                .content("{\"stocks\": [{\"name\": \"AAPL\", \"quantity\": 0}]}"));

        mockMvc.perform(post("/wallets/john/stocks/AAPL")
                        .contentType(APPLICATION_JSON)
                        .content("{\"type\": \"buy\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateWalletOnFirstBuy() throws Exception {
        mockMvc.perform(post("/wallets/john/stocks/AAPL")
                        .contentType(APPLICATION_JSON)
                        .content("{\"type\": \"buy\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("john"))
                .andExpect(jsonPath("$.stocks[0].name").value("AAPL"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));
    }

    @Test
    void shouldReturnWalletAfterBuy() throws Exception {
        mockMvc.perform(post("/wallets/john/stocks/AAPL")
                        .contentType(APPLICATION_JSON)
                        .content("{\"type\": \"buy\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks").isArray())
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));
    }
}