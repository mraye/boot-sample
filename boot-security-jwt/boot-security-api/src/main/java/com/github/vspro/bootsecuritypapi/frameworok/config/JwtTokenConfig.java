package com.github.vspro.bootsecuritypapi.frameworok.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt-token")
public class JwtTokenConfig {

    private long accessTokenDt;

    private long refreshTokenDt;


}
