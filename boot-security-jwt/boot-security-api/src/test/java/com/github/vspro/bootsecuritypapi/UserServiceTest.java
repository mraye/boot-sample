package com.github.vspro.bootsecuritypapi;


import com.github.vspro.system.persistent.domain.SysUserDo;
import com.github.vspro.system.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void loadByUserNameTest(){
        SysUserDo test = userService.getUserByUserName("test");
        Gson gson = new Gson();
        System.out.println(gson.toJson(test));
    }

}
