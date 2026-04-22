package com.github.mdutka5.simplifiedstockmarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "bank")
@AllArgsConstructor
@NoArgsConstructor
public class BankStock {
    @Id
    private String stockName;
    private Integer stockQuantity;
}
