package com.github.vspro.bootsecuritypapi.frameworok.security.authentication;

import com.github.vspro.bootsecuritypapi.frameworok.common.ResponseEntity;
import com.github.vspro.bootsecuritypapi.frameworok.exception.JwtTokenException;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        responseEntity.error("无权限访问: "+ authException.getMessage()).code(HttpStatus.FORBIDDEN.value());
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json; charset=utf-8");
        writer.write(gson.toJson(responseEntity));
        writer.flush();
    }

}
