package com.example.common;

import lombok.Data;

@Data
public class Result {

    private int code;
    private String msg;
    private Object data;

    public static Result ok(Object data){
        Result r = new Result();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }

    public static Result fail(String msg){
        Result r = new Result();
        r.code = 500;
        r.msg = msg;
        return r;
    }
}