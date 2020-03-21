package com.github.vspro.bootsecuritypapi.frameworok.common;


import lombok.Data;

@Data
public class ResponseEntity<T> {

    private T data;

    private int code;

    private String msg;

    private long timeStamp = System.currentTimeMillis();

    public ResponseEntity data(T data) {
        this.data = data;
        return this;
    }

    public ResponseEntity success(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "success";
        return this;
    }


    public ResponseEntity code(int code) {
        this.code = code;
        return this;
    }

    public ResponseEntity msg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseEntity error(T data) {
        this.data = data;
        this.code = 500;
        this.msg = "error";
        return this;
    }


}
