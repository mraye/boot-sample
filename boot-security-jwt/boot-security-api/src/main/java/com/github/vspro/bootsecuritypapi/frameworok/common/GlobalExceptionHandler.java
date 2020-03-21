package com.github.vspro.bootsecuritypapi.frameworok.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller层
 * 统一异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * @ExceptionHandler 可以指定捕获特定的异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        log.error("服务器异常: {}", e);
        return new ResponseEntity<>().error("服务器出错啦");
    }

}
