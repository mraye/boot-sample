package com.github.vspro.system.persistent.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserDo {

    private Long id;

    private String userName;

    private String password;

    private Boolean enabled;

    private Boolean atDeleted;

    private Date atCreateTime;

    private Date atModifyTime;

}