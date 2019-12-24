package com.bluetron.rxretrohttp.bean;

/**
 * Created by tongxb on 2017/12/21.
 * Api返回结果封装
 */
public class ApiResult<T> {
    public static final int CODE_SUCCESS = 200, CODE_ERROR = -1,TOKEN_INVALID = 600;
    private int code = CODE_ERROR;
    private String message;
    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }
}
