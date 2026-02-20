package com.project.syncpoint.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("DEBUG: Filter is processing request to: " + request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        System.out.println("DEBUG: Auth Header found: " + (authHeader != null));
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // 1. Your existing Tenant logic
                String tenantId = jwtUtils.getTenantIdFromJwtToken(jwt);
                TenantContext.setCurrentTenant(Long.parseLong(tenantId));

                // 2. THE MISSING PIECE: Create the "Passport"
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        "user", // Principal (can be the username)
                        null,   // Credentials (not needed for JWT)
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Authorities
                );

                // 3. Link the request details and set the context
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("DEBUG: SecurityContext set for Tenant: " + tenantId);
            }
        } catch (Exception e) {
            // Log authentication failure
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // CRITICAL: Clear the context after the request is finished
            // to prevent data leaking to the next request on this thread.
            TenantContext.clear();
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}