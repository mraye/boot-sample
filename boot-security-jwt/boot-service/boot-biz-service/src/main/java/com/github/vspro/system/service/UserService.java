package com.github.vspro.system.service;

import com.github.vspro.system.persistent.dao.SysUserDao;
import com.github.vspro.system.persistent.domain.SysUserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SysUserDao userDao;

    public SysUserDo getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }


    public Boolean insert(SysUserDo userDo){
        userDao.insert(userDo);
        int a = 1/0;
        return true;
    }

}
