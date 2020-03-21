package com.github.vspro.bootldap.framework.config;

import com.github.vspro.bootldap.framework.properties.SecondLdapProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
public class SecondLdapServerConfig {

    @Autowired
    private SecondLdapProperty ldapProperty;

    @Bean(name = "secondLdapContextSource")
    public LdapContextSource ldapContextSource() {

        LdapContextSource source = new LdapContextSource();
        source.setBase(ldapProperty.getBase());
        source.setUserDn(ldapProperty.getUserDn());
        source.setPassword(ldapProperty.getPassword());
        source.setUrl(ldapProperty.getUrl());
        return source;
    }

    @Bean(name = "secondLdapTemplate")
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    /**
     * 设置查询用户过滤条件
     * BindAuthenticator用来认证用户
     *
     * @return
     */
    @Bean(name = "secondBindAuthenticator")
    public BindAuthenticator bindAuthenticator() throws Exception {

        BindAuthenticator bindAuthenticator = new BindAuthenticator(ldapContextSource());
        LdapUserSearch search = new FilterBasedLdapUserSearch("", "(uid={0})", ldapContextSource());
        bindAuthenticator.setUserSearch(search);
        bindAuthenticator.afterPropertiesSet();
        return bindAuthenticator;
    }


    /**
     * LdapAuthoritiesPopulator用来根据用户检索用户权限
     *
     * @return
     */
    @Bean(name = "secondLdapAuthoritiesPopulator")
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {

        // 角色在哪一个节点 RDN
        // String groupSearchBase = "ou=groups";

        // 查找角色的属性节点，默认是 cn
        // 即到groupSearchBase下，查找属性为cn的值
        // String groupRoleAttribute = "cn";

        // 在角色下查找用户，默认是 uniqueMember={0}
        // String groupSearchFilter = "uniqueMember={0}";

        // 如果LDAP服务器中指定了前缀，可以不指定，默认加了ROLE_前缀
        // String rolePrefix = "";

        DefaultLdapAuthoritiesPopulator defaultAuthoritiesPopulator
                = new DefaultLdapAuthoritiesPopulator(ldapContextSource(), ldapProperty.getGroupSearchBase());
        defaultAuthoritiesPopulator.setGroupRoleAttribute(ldapProperty.getGroupRoleAttribute());
        defaultAuthoritiesPopulator.setGroupSearchFilter(ldapProperty.getGroupSearchFilter());
        defaultAuthoritiesPopulator.setRolePrefix(ldapProperty.getRolePrefix());

        return defaultAuthoritiesPopulator;
    }

    @Bean("secondLdapAuthenticationProvider")
    public LdapAuthenticationProvider ldapAuthenticationProvider() throws Exception {
        return new LdapAuthenticationProvider(bindAuthenticator(), ldapAuthoritiesPopulator());
    }


}
