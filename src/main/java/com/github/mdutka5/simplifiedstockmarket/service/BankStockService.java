package com.github.mdutka5.simplifiedstockmarket.service;

import lombok.RequiredArgsConstructor;
import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import org.springframework.stereotype.Service;
import com.github.mdutka5.simplifiedstockmarket.repository.BankStockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankStockService {
    private final BankStockRepository bankStockRepository;

    public List<BankStock> getAllStocks() {
        return bankStockRepository.findAll();
    }

    public void setBankState(Iterable<BankStock> bankStocks) {

    }
}
