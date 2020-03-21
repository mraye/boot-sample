package com.github.vspro.bootsecuritypapi.frameworok.security.token;

import com.github.vspro.bootsecuritypapi.frameworok.exception.JwtTokenException;

public interface TokenService {

    /**
     * 每调用一次就会重新生成token
     * @param userName
     * @return
     */
    JwtToken createToken(String userName);

    JwtToken refreshToken(String refreshToken);

    /**
     * 校验给定的token是否有效,如果token失效，则
     * 会抛出JwtTokenException异常
     * @param token
     * @return
     */
    boolean validate(String token) throws JwtTokenException;

}
