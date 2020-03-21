package com.github.vspro.bootsecuritypapi;

import com.github.vspro.bootsecuritypapi.frameworok.utils.JWtTokenManager;
import com.google.gson.Gson;
import org.junit.Test;

public class JwtTokenManagerTest {


    @Test
    public void generateTokenTest(){
        System.out.println(JWtTokenManager.generateToken("如何"));
    }

    @Test
    public void parseTokenTest(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlpoLkvZUiLCJpYXQiOjE1NTg3NjU4NTUsImV4cCI6MTU1ODc3MzA1NX0.6aiwEKCELfLU9lz4prNzkFHj9cbnkkD4i_NziO4tMxublOBK6WX_6u_xRQTJW6eke-tfmrnFyv9nzf0y55HeSA";
        Gson gson = new Gson();
        String s = gson.toJson(JWtTokenManager.getUserNameFromToken(token));
        System.out.println(s);

    }


}
