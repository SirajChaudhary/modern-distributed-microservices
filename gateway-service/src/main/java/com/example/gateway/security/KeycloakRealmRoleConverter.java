package com.example.gateway.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Extracts realm roles from Keycloak JWT and converts them
 * into Spring Security authorities.
 *
 * Example:
 *
 * realm_access:
 * {
 *   "roles": ["ADMIN", "STUDENT"]
 * }
 *
 * becomes:
 *
 * ROLE_ADMIN
 * ROLE_STUDENT
 */
public class KeycloakRealmRoleConverter
        implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(
            Jwt jwt) {

        Map<String, Object> realmAccess =
                jwt.getClaimAsMap("realm_access");

        if (realmAccess == null) {
            return Collections.emptyList();
        }

        Object rolesObject =
                realmAccess.get("roles");

        if (!(rolesObject instanceof List<?> roles)) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(role ->
                        new SimpleGrantedAuthority(
                                "ROLE_" + role
                        )
                )
                .collect(Collectors.toList());
    }
}