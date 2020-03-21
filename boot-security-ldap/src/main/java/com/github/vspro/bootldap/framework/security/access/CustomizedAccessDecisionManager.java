package com.github.vspro.bootldap.framework.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomizedAccessDecisionManager implements AccessDecisionManager {

    private static final String PREFIX = "ROLE_";

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        boolean result = false;
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : configAttributes) {
            if (this.supports(attribute)) {

                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        result = true;
                        return;
                    }
                }
            }
        }

        if (!result){
            throw new AccessDeniedException("AbstractAccessDecisionManager.accessDenied Access is denied");
        }

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null)
                && attribute.getAttribute().startsWith(PREFIX)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities();
    }
}
