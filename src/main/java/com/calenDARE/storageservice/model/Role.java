package com.calenDARE.storageservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.calenDARE.storageservice.model.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                //.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(ADMIN_READ.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(ADMIN_CREATE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(ADMIN_UPDATE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(ADMIN_DELETE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(MANAGER_CREATE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(MANAGER_UPDATE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(MANAGER_DELETE.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(MANAGER_READ.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
