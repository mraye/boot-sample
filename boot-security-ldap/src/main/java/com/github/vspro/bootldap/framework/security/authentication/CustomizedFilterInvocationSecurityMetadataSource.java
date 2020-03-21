package com.github.vspro.bootldap.framework.security.authentication;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 自定义角色和受保护request的映射关系
 */
public class CustomizedFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();


    public CustomizedFilterInvocationSecurityMetadataSource() {
        init();
    }

    private void init() {
        //TODO 这里需要换成不同用户对应不同的角色
        //TODO 目前这里有一点问题
        //TODO 可以在认证成功的时候到数据库中加载用户和角色的关系，放在session中or some what
        RequestMatcher adminMatcher = new AntPathRequestMatcher("/admin/**");
        Set<ConfigAttribute> adminSet = new HashSet<>();
        adminSet.add(new SecurityConfig("ROLE_ADMIN"));
        requestMap.put(adminMatcher, adminSet);

        RequestMatcher userMatcher = new AntPathRequestMatcher("/**");
        Set<ConfigAttribute> userSet = new HashSet<>();
        adminSet.add(new SecurityConfig("ROLE_USER"));
        requestMap.put(userMatcher, userSet);

    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }


}
