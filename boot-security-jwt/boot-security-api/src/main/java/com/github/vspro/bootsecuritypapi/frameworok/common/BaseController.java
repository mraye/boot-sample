package com.github.vspro.bootsecuritypapi.frameworok.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    /**
     * 获取用户名
     * @return
     */
    protected String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }


    public <T> ResponseEntity<T> error(T data) {
        return new ResponseEntity<>().error(data).msg("服务器内部错误");
    }

    public <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>().success(data);
    }
}
