package com.github.vspro.bootsecuritypapi;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {


    @Test
    public void passwordGenerateTest(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("111111");
        System.out.println(pwd);
    }
}
