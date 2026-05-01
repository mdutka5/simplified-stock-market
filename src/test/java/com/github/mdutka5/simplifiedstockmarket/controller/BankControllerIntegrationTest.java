package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.BaseIntegrationTest;
import com.github.mdutka5.simplifiedstockmarket.repository.BankStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BankControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BankStockRepository bankStockRepository;

    @BeforeEach
    void setUp() {
        bankStockRepository.deleteAll();
    }

    @Test
    void shouldReturnEmptyBankOnStart() throws Exception {
        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks").isArray())
                .andExpect(jsonPath("$.stocks").isEmpty());
    }

    @Test
    void shouldSetBankState() throws Exception {
        mockMvc.perform(post("/stocks")
                        .contentType(APPLICATION_JSON)
                        .content("{\"stocks\": [{\"name\": \"AAPL\", \"quantity\": 100}]}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks[0].name").value("AAPL"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(100));
    }
}
