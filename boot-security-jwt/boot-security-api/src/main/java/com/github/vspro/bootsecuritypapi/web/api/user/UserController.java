package com.github.vspro.bootsecuritypapi.web.api.user;


import com.github.vspro.bootsecuritypapi.frameworok.common.BaseController;
import com.github.vspro.bootsecuritypapi.frameworok.common.ResponseEntity;
import com.github.vspro.system.persistent.domain.SysUserDo;
import com.github.vspro.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public ResponseEntity<SysUserDo> userInfo(){
        return success(userService.getUserByUserName(getUserName()));
    }

    @RequestMapping("/insert")
    public ResponseEntity<Boolean> insert(){
        SysUserDo sysUserDo = new SysUserDo();
        sysUserDo.setUserName("aaa");
        return success(userService.insert(sysUserDo));
    }


}
