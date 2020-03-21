package com.github.vspro.bootsecuritypapi;


import com.github.vspro.bootsecuritypapi.frameworok.security.token.JwtToken;
import com.github.vspro.bootsecuritypapi.frameworok.security.token.TokenService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    TokenService tokenService;

    @Test
    public void createTokenTest(){
        Gson gson = new Gson();
        JwtToken token = tokenService.createToken("Tom Jack");
        System.out.println(gson.toJson(token));
    }
}
