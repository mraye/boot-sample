package com.github.vspro.bootsecuritypapi.frameworok.security.token.service;

import com.github.vspro.bootsecuritypapi.frameworok.config.JwtTokenConfig;
import com.github.vspro.bootsecuritypapi.frameworok.exception.JwtTokenException;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.JwtToken;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.TokenService;
import com.github.vspro.bootsecuritypapi.frameworok.utils.JWtTokenManager;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtBasedTokenService implements TokenService {

    private static final String TOKEN_KEY_PREFIX = "token:";

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public JwtToken createToken(String userName) {
        return generateToken(userName, null);
    }

    @Override
    public JwtToken refreshToken(String refreshToken) {

        if (JWtTokenManager.isTokenExpired(refreshToken)) {
            throw new JwtTokenException("refreshToken is invalid!!");
        }
        String userName = JWtTokenManager.getUserNameFromToken(refreshToken);
        return generateToken(userName, refreshToken);
    }

    public JwtToken generateToken(String userName, String refreshToken) {
        Date now = new Date();
        long expired = System.currentTimeMillis() + jwtTokenConfig.getAccessTokenDt();
        String accessToken = JWtTokenManager.generateToken(userName, now, expired);

        String newRefreshToken;
        if (StringUtils.isBlank(refreshToken)) {
            long refreshExpired = System.currentTimeMillis() + jwtTokenConfig.getRefreshTokenDt();
            newRefreshToken = JWtTokenManager.generateToken(userName, now, refreshExpired);
        } else {
            //refreshToken不过期，则不重新生成
            newRefreshToken = refreshToken;
        }
        JwtToken token = new JwtToken(accessToken, newRefreshToken, expired);
        String key = TOKEN_KEY_PREFIX + userName;
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(token));
        return token;
    }


    @Override
    public boolean validate(String accessToken) throws JwtTokenException{

        String userName = JWtTokenManager.getUserNameFromToken(accessToken);
        if (StringUtils.isNotBlank(userName)) {
            String key = TOKEN_KEY_PREFIX + userName;
            String value = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(value)) {
                Gson gson = new Gson();
                JwtToken jwtToken = gson.fromJson(value, JwtToken.class);
                if (accessToken.equals(jwtToken.getAccessToken())) {
                    return true;
                }else {
                    throw new JwtTokenException("Token had benn expired!");
                }
            }
        }
        return false;
    }

}
