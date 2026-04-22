package com.github.mdutka5.simplifiedstockmarket.repository;

import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStockRepository extends JpaRepository<BankStock, String> {
}
