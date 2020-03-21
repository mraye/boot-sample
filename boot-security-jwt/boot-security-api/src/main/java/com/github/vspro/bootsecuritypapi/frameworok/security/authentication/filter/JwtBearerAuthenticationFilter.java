package com.github.vspro.bootsecuritypapi.frameworok.security.authentication.filter;

import com.github.vspro.bootsecuritypapi.frameworok.common.ResponseEntity;
import com.github.vspro.bootsecuritypapi.frameworok.exception.JwtTokenException;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.TokenService;
import com.github.vspro.bootsecuritypapi.frameworok.utils.JWtTokenManager;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class JwtBearerAuthenticationFilter extends BasicAuthenticationFilter {


    private TokenService tokenService;

    public JwtBearerAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = extractTokenFromHeader(header);
            if (StringUtils.isEmpty(token)) {
                throw new BadCredentialsException("Invalid authentication token");
            }

            String userName = JWtTokenManager.getUserNameFromToken(token);
            if (StringUtils.isEmpty(userName)) {
                throw new BadCredentialsException("Invalid authentication token");
            }

            if (!JWtTokenManager.isTokenExpired(token) && tokenService.validate(token)) {
                //如果认证成功
                UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(userName,
                        "", new HashSet<>());
                SecurityContextHolder.getContext().setAuthentication(upaToken);
            } else {
                throw new JwtTokenException("Invalid authentication token");
            }
        } catch (BadCredentialsException | JwtTokenException e) {
            unsuccessfulAuthentication(request, response, e);
            return;
        }

        //其他异常继续执行....
        chain.doFilter(request, response);

    }


    private String extractTokenFromHeader(String header) {
        String token = header.substring(7);
        int delim = token.indexOf(".");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid authentication token");
        }
        return token;
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException, ServletException {

        String error = "";
        if (e instanceof BadCredentialsException) {
            error = "用户认证失败，请联系管理员!";
        } else if (e instanceof JwtTokenException) {
            error = "无效的token!!";
        }

        ResponseEntity<String> responseEntity = new ResponseEntity<>().error(error);
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json; charset=utf-8");
        writer.write(gson.toJson(responseEntity));
        writer.flush();

    }

}
