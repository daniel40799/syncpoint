package com.project.syncpoint.features.inventory;

import com.project.syncpoint.BaseIntegrationTest;
import com.project.syncpoint.features.inventory.model.Asset;
import com.project.syncpoint.features.inventory.service.AssetService;
import com.project.syncpoint.infrastructure.security.TenantContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class AssetTenantIsolationTest extends BaseIntegrationTest {

    @Autowired
    private AssetService assetService;

    @Test
    void shouldOnlyReturnAssetsForCurrentTenant() {
        // 1. Create asset for Tenant 100
        TenantContext.setCurrentTenant(100L);
        Asset a1 = new Asset();
        a1.setName("Tenant 100 Laptop");
        assetService.createAsset(a1);

        // 2. Create asset for Tenant 200
        TenantContext.setCurrentTenant(200L);
        Asset a2 = new Asset();
        a2.setName("Tenant 200 Server");
        assetService.createAsset(a2);

        // 3. Verify Tenant 200 ONLY sees their asset
        List<Asset> assets = assetService.getAllAssets();

        assertThat(assets).hasSize(1);
        assertThat(assets.get(0).getName()).isEqualTo("Tenant 200 Server");

        TenantContext.clear();
    }
}