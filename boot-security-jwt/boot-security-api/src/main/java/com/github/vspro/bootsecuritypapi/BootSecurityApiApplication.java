package com.github.vspro.bootsecuritypapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.github.vspro.**.persistent.dao"})
@ComponentScan(basePackages = {"com.github.vspro.**.service","com.github.vspro.bootsecuritypapi"})
public class BootSecurityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityApiApplication.class, args);
    }

}
