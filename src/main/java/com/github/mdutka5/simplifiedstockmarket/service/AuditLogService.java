package com.github.mdutka5.simplifiedstockmarket.service;

import com.github.mdutka5.simplifiedstockmarket.dto.response.AuditEntryResponse;
import com.github.mdutka5.simplifiedstockmarket.dto.response.AuditLogResponse;
import lombok.RequiredArgsConstructor;
import com.github.mdutka5.simplifiedstockmarket.model.AuditLog;
import org.springframework.stereotype.Service;
import com.github.mdutka5.simplifiedstockmarket.repository.AuditLogRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logActivity(String type, String walletId, String stockName) {
        auditLogRepository.save(
                AuditLog.builder()
                        .operationType(type)
                        .walletId(walletId)
                        .stockName(stockName)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public AuditLogResponse getAllLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();

        List<AuditEntryResponse> logs = auditLogs
                .stream()
                .map(auditLog -> new AuditEntryResponse(
                        auditLog.getOperationType(),
                        auditLog.getWalletId(),
                        auditLog.getStockName()
                ))
                .toList();

        return new AuditLogResponse(logs);
    }
}
