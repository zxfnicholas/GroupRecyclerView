package com.che.grouprecycleview.response;

/**
 * Created by yutianran on 16/2/27.
 */
public class BaseResponse {


    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
