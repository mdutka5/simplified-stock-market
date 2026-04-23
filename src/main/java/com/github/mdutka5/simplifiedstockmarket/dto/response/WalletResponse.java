package com.github.mdutka5.simplifiedstockmarket.dto.response;

import com.github.mdutka5.simplifiedstockmarket.dto.common.StockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private String id;
    private List<StockDto> stocks;
}
