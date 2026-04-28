package com.github.mdutka5.simplifiedstockmarket.service;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import com.github.mdutka5.simplifiedstockmarket.dto.response.WalletResponse;
import com.github.mdutka5.simplifiedstockmarket.exception.BadRequestException;
import com.github.mdutka5.simplifiedstockmarket.exception.NotFoundException;
import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import com.github.mdutka5.simplifiedstockmarket.model.Wallet;
import com.github.mdutka5.simplifiedstockmarket.model.WalletStock;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletStockRepository walletStockRepository;
    private final BankStockService bankStockService;
    private final AuditLogService auditLogService;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Integer getWalletStock(String walletId, String stockName) {
        walletRepository.findById(walletId)
                .orElseThrow(() ->  new NotFoundException("Wallet " + walletId + " does not exist."));

        return walletStockRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .orElseThrow(() -> new NotFoundException("Stock " + stockName + " not found in wallet " + walletId))
                .getQuantity();
    }

    @Transactional
    public void trade(String walletId, String stockName, String type) {
        BankStock bankStock = bankStockService.findByStockName(stockName)
                .orElseThrow(() -> new NotFoundException("Stock" + stockName + "does not exist."));

        if (type.equals("buy")) {
            if (bankStock.getStockQuantity() == 0)
                throw new BadRequestException("Stock's (" + stockName + ") quantity is zero.");

            if (walletRepository.findById(walletId).isEmpty())
                walletRepository.save(new Wallet(walletId));

            Optional<WalletStock> existingStock = walletStockRepository.findByWalletIdAndStockName(walletId, stockName);

            if (existingStock.isEmpty()) {
                walletStockRepository.save(WalletStock.builder()
                        .walletId(walletId)
                        .stockName(stockName)
                        .quantity(1)
                        .build());
            }
            else {
                WalletStock stock = existingStock.get();
                stock.setQuantity(stock.getQuantity() + 1);
                walletStockRepository.save(stock);
            }

            bankStock.setStockQuantity(bankStock.getStockQuantity() - 1);
            bankStockService.updateStock(bankStock);

            auditLogService.logActivity(type, walletId, stockName);

        } else if (type.equals("sell")) {
            walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet " + walletId + " does not exist."));

            WalletStock stock = walletStockRepository.findByWalletIdAndStockName(walletId, stockName)
                    .orElseThrow(() -> new NotFoundException("Stock " + stockName + " not found in wallet " + walletId));

            if (stock.getQuantity() == 0)
                throw new BadRequestException("Stock's (" + stockName + ") quantity is zero.");

            stock.setQuantity(stock.getQuantity() - 1);
            walletStockRepository.save(stock);
            bankStock.setStockQuantity(bankStock.getStockQuantity() + 1);
            bankStockService.updateStock(bankStock);

            auditLogService.logActivity(type, walletId, stockName);

        } else {
            throw new BadRequestException("Operation type " + type + " is invalid. Should be (sell|buy).");
        }
    }
}
