package com.github.vspro.bootsecuritypapi.frameworok.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.function.Function;

public class JWtTokenManager {


    private static String SALT = "salt";

    public static int DURATION = 7200;

    public static String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .signWith(SignatureAlgorithm.HS512, SALT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DURATION * 1000))
                .compact();
    }

    /**
     * @param userName
     * @param issuedAt token发布日期
     * @param expiration 过期时间戳(ms)
     * @return
     */
    public static String generateToken(String userName, Date issuedAt, long expiration) {
        return Jwts.builder()
                .setSubject(userName)
                .signWith(SignatureAlgorithm.HS512, SALT)
                .setIssuedAt(issuedAt)
                .setExpiration(new Date(expiration))
                .compact();
    }


    public static Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SALT)
                .parseClaimsJws(token)
                .getBody();
    }

    public static <T> T getPropertyFromClaim(String token, Function<Claims, T> fun){
        Claims claims = getClaimsFromToken(token);
        return fun.apply(claims);
    }


    public static String getUserNameFromToken(String token){
        return getPropertyFromClaim(token, Claims::getSubject);
    }

    public static boolean isTokenExpired(String token){
        Date date = getPropertyFromClaim(token, Claims::getExpiration);
        return date.before(new Date());
    }

}
