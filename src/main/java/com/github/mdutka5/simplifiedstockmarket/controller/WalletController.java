package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.dto.request.TradeRequest;
import com.github.mdutka5.simplifiedstockmarket.dto.response.WalletResponse;
import com.github.mdutka5.simplifiedstockmarket.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable String walletId) {
        return ResponseEntity.ok(walletService.getWallet(walletId));
    }

    @GetMapping("/{walletId}/stocks/{stockName}")
    public ResponseEntity<Integer> getWalletStock(
            @PathVariable String walletId,
            @PathVariable String stockName) {
        return ResponseEntity.ok(walletService.getWalletStock(walletId, stockName));
    }

    @PostMapping("/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> trade(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @RequestBody TradeRequest request) {
        walletService.trade(walletId, stockName, request.getType());
        return ResponseEntity.ok().build();
    }
}