package com.github.vspro.bootsecuritypapi.frameworok.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class JwtUser implements UserDetails {

    private Long id;
    private String password;
    private final String username;
    private final boolean enabled;
    private final Set<GrantedAuthority> authorities;

    public JwtUser(String password, String username, Set<GrantedAuthority> authorities) {
        this(password, username, true, authorities);
    }

    public JwtUser(String password, String username, boolean enabled, Set<GrantedAuthority> authorities) {
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
