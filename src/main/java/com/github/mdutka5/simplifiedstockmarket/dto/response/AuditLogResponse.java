package com.github.mdutka5.simplifiedstockmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {
    private List<AuditEntryResponse> log;
}
