package com.github.vspro.bootsecuritypapi.frameworok.security.authentication.filter;

import com.github.vspro.bootsecuritypapi.frameworok.common.ResponseEntity;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.JwtToken;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.TokenService;
import com.github.vspro.bootsecuritypapi.frameworok.security.user.JwtUser;
import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private TokenService tokenService;

    public JwtUsernamePasswordAuthenticationFilter(TokenService tokenService) {
        super();
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        if (authResult.getPrincipal() instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
            String userName = jwtUser.getUsername();
            JwtToken token = tokenService.createToken(userName);
            ResponseEntity<JwtToken> responseEntity = new ResponseEntity<>().success(token);
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();
            response.setContentType("application/json; charset=utf-8");
            writer.write(gson.toJson(responseEntity));
            writer.flush();

        } else {
            throw new UsernameNotFoundException("用户认证失败，请联系管理员!");
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String error = "用户认证失败，请联系管理员!";
        ResponseEntity<String> responseEntity = new ResponseEntity<>().error(error).code(500);
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json; charset=utf-8");
        writer.write(gson.toJson(responseEntity));
        writer.flush();

    }
}
