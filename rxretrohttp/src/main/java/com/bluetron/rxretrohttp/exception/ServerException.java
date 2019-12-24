package com.bluetron.rxretrohttp.exception;

import java.io.IOException;

/**
 * @author tongxb
 * @date 2018/3/22
 */

public class ServerException extends IOException {
    private int code;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServerException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
