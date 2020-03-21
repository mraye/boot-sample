package com.github.vspro.system.persistent.dao;

import com.github.vspro.system.persistent.domain.SysUserDo;

public interface SysUserDao {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserDo record);

    int insertSelective(SysUserDo record);

    SysUserDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserDo record);

    int updateByPrimaryKey(SysUserDo record);

    SysUserDo getUserByUserName(String userName);
}