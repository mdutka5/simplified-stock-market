package com.github.mdutka5.simplifiedstockmarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "wallet_stocks")
@AllArgsConstructor
@NoArgsConstructor
public class WalletStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String walletId;
    private String stockName;
    private Integer quantity;

}
