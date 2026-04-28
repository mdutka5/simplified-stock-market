package com.github.mdutka5.simplifiedstockmarket.repository;

import com.github.mdutka5.simplifiedstockmarket.model.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
    List<WalletStock> findAllByWalletId(String walletId);
    Optional<WalletStock> findByWalletIdAndStockName(String walletId, String stockName);
}
