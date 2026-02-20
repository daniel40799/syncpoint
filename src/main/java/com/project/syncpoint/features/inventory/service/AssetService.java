package com.project.syncpoint.features.inventory.service;

import com.project.syncpoint.features.audit.messaging.AuditProducer;
import com.project.syncpoint.features.inventory.model.Asset;
import com.project.syncpoint.features.inventory.repository.AssetRepository;
import com.project.syncpoint.infrastructure.security.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AuditProducer auditProducer;

    @Cacheable(value = "assets", key = "T(com.project.syncpoint.infrastructure.security.TenantContext).getCurrentTenant()")
    public List<Asset> getAllAssets() {
        // When this runs, the AOP Aspect (PersistenceConfig)
        // has already turned on the Filter for the current tenant.
        return assetRepository.findAll();
    }

    @Transactional // Ensures database integrity
    @CacheEvict(value = "assets", key = "T(com.project.syncpoint.infrastructure.security.TenantContext).getCurrentTenant()")
    public Asset createAsset(Asset asset) {
        asset.setTenantId(TenantContext.getCurrentTenant());
        Asset savedAsset = assetRepository.save(asset);

        // Fire and Forget: Send log to Kafka
        String auditMsg = String.format("Tenant %d created asset: %s",
                asset.getTenantId(), asset.getName());
        auditProducer.sendAuditLog(auditMsg);

        return savedAsset;
    }

    // Helper method for the cache key
    public String getTenantKey() {
        return "tenant_" + TenantContext.getCurrentTenant();
    }
}