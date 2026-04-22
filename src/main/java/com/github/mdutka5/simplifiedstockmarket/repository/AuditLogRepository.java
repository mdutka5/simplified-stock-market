package com.github.mdutka5.simplifiedstockmarket.repository;

import com.github.mdutka5.simplifiedstockmarket.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
