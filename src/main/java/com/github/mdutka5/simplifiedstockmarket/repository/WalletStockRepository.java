package com.github.mdutka5.simplifiedstockmarket.repository;

import com.github.mdutka5.simplifiedstockmarket.model.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
}
