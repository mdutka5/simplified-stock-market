package com.github.mdutka5.simplifiedstockmarket.service;

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
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
