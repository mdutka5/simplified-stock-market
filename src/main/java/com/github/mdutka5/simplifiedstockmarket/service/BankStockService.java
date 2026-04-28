package com.github.mdutka5.simplifiedstockmarket.service;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import com.github.mdutka5.simplifiedstockmarket.dto.response.BankResponse;
import lombok.RequiredArgsConstructor;
import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import org.springframework.stereotype.Service;
import com.github.mdutka5.simplifiedstockmarket.repository.BankStockRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankStockService {
    private final BankStockRepository bankStockRepository;

    @Transactional(readOnly = true)
    public BankResponse getAllStocks() {
        List<BankStock> stocks = bankStockRepository.findAll();
        List<StockDto> stockDtos = stocks
                .stream()
                .map(stock -> new StockDto(
                        stock.getStockName(),
                        stock.getStockQuantity()
                )).toList();
        return new BankResponse(stockDtos);
    }

    @Transactional
    public void setBankState(List<StockDto> stocks) {
        bankStockRepository.deleteAll();
        List<BankStock> bankStocks = stocks.stream()
                .map(dto -> new BankStock(
                        dto.getName(),
                        dto.getQuantity()
                )).toList();
        bankStockRepository.saveAll(bankStocks);
    }

    @Transactional(readOnly = true)
    public Optional<BankStock> findByStockName(String stockName) {
        return bankStockRepository.findById(stockName);
    }

    @Transactional
    public void updateStock(BankStock bankStock) {
        bankStockRepository.save(bankStock);
    }
}
