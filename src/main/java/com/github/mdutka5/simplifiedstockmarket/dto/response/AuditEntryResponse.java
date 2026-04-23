package com.github.mdutka5.simplifiedstockmarket.dto.response;

import lombok.Data;

@Data
public class AuditEntryResponse {
    private String type;
    private String walletId;
    private String stockName;
}
