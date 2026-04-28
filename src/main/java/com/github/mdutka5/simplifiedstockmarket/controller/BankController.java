package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.dto.request.SetBankStateRequest;
import com.github.mdutka5.simplifiedstockmarket.dto.response.BankResponse;
import com.github.mdutka5.simplifiedstockmarket.service.BankStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class BankController {
    private final BankStockService bankStockService;

    @GetMapping
    public ResponseEntity<BankResponse> getBankState() {
        return ResponseEntity.ok(bankStockService.getAllStocks());
    }

    @PostMapping
    public ResponseEntity<Void> setBankState(@RequestBody SetBankStateRequest request) {
        bankStockService.setBankState(request.getStocks());
        return ResponseEntity.ok().build();
    }
}