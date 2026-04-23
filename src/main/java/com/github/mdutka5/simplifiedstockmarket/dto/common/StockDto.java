package com.github.mdutka5.simplifiedstockmarket.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private String name;
    private Integer quantity;
}
