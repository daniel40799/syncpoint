package com.project.syncpoint.features.auth.security;

import com.project.syncpoint.infrastructure.security.TenantContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TenantJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String tenantIdStr = jwt.getClaimAsString("tenantId");

        if (tenantIdStr != null) {
            Long tenantId = Long.parseLong(tenantIdStr);
            TenantContext.setCurrentTenant(tenantId);
            // ADD THIS LOG:
            System.out.println("🚀 Security Context Set - Tenant ID: " + tenantId);
        }

        return new JwtAuthenticationToken(jwt, List.of());
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // For now, return empty or map Keycloak roles here
        return List.of();
    }
}