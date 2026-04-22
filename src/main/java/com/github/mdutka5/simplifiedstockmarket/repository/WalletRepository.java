package com.github.mdutka5.simplifiedstockmarket.repository;

import com.github.mdutka5.simplifiedstockmarket.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
