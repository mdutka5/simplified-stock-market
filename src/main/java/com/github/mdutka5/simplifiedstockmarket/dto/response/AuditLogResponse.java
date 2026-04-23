package com.github.mdutka5.simplifiedstockmarket.dto.response;

import lombok.Data;

@Data
public class AuditLogResponse {
    private List<AuditEntryResponse> log;
}
