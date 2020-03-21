package com.github.vspro.bootsecuritypapi.frameworok.config;


import com.github.vspro.bootsecuritypapi.frameworok.security.access.CustomizedAccessDeniedHandler;
import com.github.vspro.bootsecuritypapi.frameworok.security.authentication.JwtAuthenticationEntryPoint;
import com.github.vspro.bootsecuritypapi.frameworok.security.authentication.filter.JwtBearerAuthenticationFilter;
import com.github.vspro.bootsecuritypapi.frameworok.security.authentication.filter.JwtUsernamePasswordAuthenticationFilter;
import com.github.vspro.bootsecuritypapi.frameworok.security.authentication.provider.CustomizedDaoAuthenticationProvider;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "customizedUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        CustomizedDaoAuthenticationProvider provider = new CustomizedDaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider())
                .userDetailsService(userDetailsService);
    }


    @Bean
    public JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter() throws Exception {
        JwtUsernamePasswordAuthenticationFilter filter = new JwtUsernamePasswordAuthenticationFilter(tokenService);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .addFilter(new JwtBearerAuthenticationFilter(authenticationManager(), tokenService))
                .addFilter(jwtUsernamePasswordAuthenticationFilter())
                .exceptionHandling().accessDeniedHandler(new CustomizedAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
