package com.github.vspro.bootsecuritypapi.frameworok.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {


    private String accessToken;

    /**
     * 用来刷新accessToken
     * 一般生成的refreshToken过期时间长于accessToken
     */
    private String refreshToken;

    /**
     * accessToken过期时间
     */
    private long expiredIn;

}
