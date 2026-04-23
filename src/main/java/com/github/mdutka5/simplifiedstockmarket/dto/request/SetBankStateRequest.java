package com.github.mdutka5.simplifiedstockmarket.dto.request;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import lombok.Data;

import java.util.List;

@Data
public class SetBankStateRequest {
    private List<StockDto> stocks;
}
