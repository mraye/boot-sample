package com.github.vspro.bootsecuritypapi.frameworok.security.access;

import com.github.vspro.bootsecuritypapi.frameworok.common.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        responseEntity.error(HttpStatus.FORBIDDEN.getReasonPhrase()).code(HttpStatus.FORBIDDEN.value());
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json; charset=utf-8");
        writer.write(gson.toJson(responseEntity));
        writer.flush();
    }
}
