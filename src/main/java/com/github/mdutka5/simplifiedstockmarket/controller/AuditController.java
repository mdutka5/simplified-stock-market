package com.github.mdutka5.simplifiedstockmarket.controller;

import com.github.mdutka5.simplifiedstockmarket.dto.response.AuditLogResponse;
import com.github.mdutka5.simplifiedstockmarket.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class AuditController {
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<AuditLogResponse> getLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
}