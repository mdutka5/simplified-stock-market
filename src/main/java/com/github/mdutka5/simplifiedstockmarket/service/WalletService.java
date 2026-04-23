package com.github.mdutka5.simplifiedstockmarket.service;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import com.github.mdutka5.simplifiedstockmarket.dto.response.WalletResponse;
import com.github.mdutka5.simplifiedstockmarket.exception.NotFoundException;
import com.github.mdutka5.simplifiedstockmarket.model.WalletStock;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletStockRepository walletStockRepository;
    private final BankStockService bankStockService;
    private final AuditLogService auditLogService;

    public WalletResponse getWallet(String walletId) {
        walletRepository.findById(walletId)
                .orElseThrow(() ->  new NotFoundException("Wallet " + walletId + " does not exist."));

        List<WalletStock> walletStocks = walletStockRepository.findAllByWalletId(walletId);
        List<StockDto> stocksDTOs = walletStocks.stream()
                .map(stock -> new StockDto(
                        stock.getStockName(),
                        stock.getQuantity()
                )).toList();
        return new WalletResponse(walletId, stocksDTOs);
    }

}
