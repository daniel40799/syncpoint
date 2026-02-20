package com.project.syncpoint.features.inventory.repository;

import com.project.syncpoint.features.inventory.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    // Standard CRUD methods are inherited.
    // Even 'findAll()' will be intercepted by our Tenant Filter.
    Optional<Asset> findBySerialNumber(String serialNumber);
}