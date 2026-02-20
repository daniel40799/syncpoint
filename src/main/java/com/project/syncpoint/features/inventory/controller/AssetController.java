package com.project.syncpoint.features.inventory.controller;

import com.project.syncpoint.features.inventory.model.Asset;
import com.project.syncpoint.features.inventory.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset created = assetService.createAsset(asset);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/test-token")
    public String getTestToken() {
        // This generates a token for Tenant 101 that expires in 1 hour
        return io.jsonwebtoken.Jwts.builder()
                .setSubject("admin")
                .claim("tenantId", "101")
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 3600000))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970".getBytes()))
                .compact();
    }
}