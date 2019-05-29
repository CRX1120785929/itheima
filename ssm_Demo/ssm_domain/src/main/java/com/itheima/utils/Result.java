package com.itheima.utils;

import java.io.Serializable;

public class Result implements Serializable {

    private boolean success; //判断该变量
    private String message;  //返回的字符串

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
