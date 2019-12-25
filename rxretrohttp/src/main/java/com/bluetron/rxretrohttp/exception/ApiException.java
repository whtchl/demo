package com.bluetron.rxretrohttp.exception;

import android.accounts.NetworkErrorException;

import com.bluetron.rxretrohttp.R;
import com.bluetron.rxretrohttp.RxRetroHttp;
import com.bluetron.rxretrohttp.bean.ApiResult;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * @author tongxb
 * @date 2018/3/20
 */

public class ApiException extends IOException {
    public static final int SERVER_ERROR = ApiResult.CODE_ERROR, NOT_CARE = 1000, NETWORK_ERROR =
            NOT_CARE + 1, TIMEOUT_ERROR = NETWORK_ERROR + 1, NETWORK_NOT_OPEN = TIMEOUT_ERROR + 1;
    //对应HTTP的状态码
    private static final int BADREQUEST = 400, UNAUTHORIZED = 401, FORBIDDEN = 403, NOT_FOUND =
            404, METHOD_NOT_ALLOWED = 405, REQUEST_TIMEOUT = 408, INTERNAL_SERVER_ERROR = 500,
            BAD_GATEWAY = 502, SERVICE_UNAVAILABLE = 503, GATEWAY_TIMEOUT = 504;

    private final int code;
    private String displayMessage;
    private String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return message;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg;
    }

    public static boolean isSuccess(ApiResult apiResult) {
        return apiResult != null && apiResult.isSuccess();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
            ex.message = httpException.getMessage();
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.message = resultException.getMessage();
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, NETWORK_ERROR);
            ex.message =  RxRetroHttp.sApplication.getString(R.string.okhttp_error_conn_fail);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, TIMEOUT_ERROR);
            ex.message =  RxRetroHttp.sApplication.getString(R.string.okhttp_error_conn_time_out);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, TIMEOUT_ERROR);
            ex.message =  RxRetroHttp.sApplication.getString(R.string.okhttp_error_conn_time_out);
            return ex;
        } else if (e instanceof ApiException){
            return (ApiException) e;
        } else {
            ex = new ApiException(e, NOT_CARE);
            ex.message =  RxRetroHttp.sApplication.getString(R.string.okhttp_error_server);
            return ex;
        }
    }

    public static ApiException getUnHandleException() {
        return new ApiException(NOT_CARE, "");
    }
}
