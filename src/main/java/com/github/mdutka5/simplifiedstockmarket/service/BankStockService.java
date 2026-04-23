package com.github.mdutka5.simplifiedstockmarket.service;

import lombok.RequiredArgsConstructor;
import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import org.springframework.stereotype.Service;
import com.github.mdutka5.simplifiedstockmarket.repository.BankStockRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankStockService {
    private final BankStockRepository bankStockRepository;

    public List<BankStock> getAllStocks() {
        return bankStockRepository.findAll();
    }

    public void setBankState(List<BankStock> bankStocks) {
        bankStockRepository.deleteAll();
        bankStockRepository.saveAll(bankStocks);
    }

    public Optional<BankStock> findByStockName(String stockName) {
        return bankStockRepository.findById(stockName);
    }

    public void updateStock(BankStock bankStock) {
        bankStockRepository.save(bankStock);
    }
}
