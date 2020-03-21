package com.github.vspro.bootldap.framework.config;


import com.github.vspro.bootldap.framework.security.access.CustomizedAccessDecisionManager;
import com.github.vspro.bootldap.framework.security.authentication.CustomizedFilterInvocationSecurityMetadataSource;
import com.github.vspro.bootldap.framework.security.authentication.CustomizedFilterSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "firstLdapAuthenticationProvider")
    private AuthenticationProvider firstLdapAuthenticationProvider;

    @Resource(name = "secondLdapAuthenticationProvider")
    private AuthenticationProvider secondLdapAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(firstLdapAuthenticationProvider)
                .authenticationProvider(secondLdapAuthenticationProvider)
                .ldapAuthentication()
                //这里要!!指定用户在哪一个节点下查找
                .userDnPatterns(Arrays.asList(
                        "uid={0}, ou=people",
                        "uid={0}, ou=account"
                ).toArray(new String[2]));
    }

    @Bean(name = "customizedFilterSecurityInterceptor")
    public CustomizedFilterSecurityInterceptor interceptor(){
        CustomizedFilterSecurityInterceptor interceptor= new CustomizedFilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(new CustomizedFilterInvocationSecurityMetadataSource());
        interceptor.setAccessDecisionManager(new CustomizedAccessDecisionManager());
        return interceptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDeny")
                .and()
                .addFilterBefore(interceptor(), FilterSecurityInterceptor.class)
                .formLogin()
                .successForwardUrl("/home")
                .and()
                .logout();
        http.csrf().disable();
    }
}
