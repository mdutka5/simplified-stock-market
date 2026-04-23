package com.github.mdutka5.simplifiedstockmarket.dto.response;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import lombok.Data;

import java.util.List;

@Data
public class BankResponse {
    private List<StockDto> stocks;
}
