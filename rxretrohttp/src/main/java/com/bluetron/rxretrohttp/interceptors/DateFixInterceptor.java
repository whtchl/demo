package com.bluetron.rxretrohttp.interceptors;

import com.bluetron.rxretrohttp.util.RetroDateUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author tongxb
 * @date 2018/3/22
 * 服务器时间同步修正拦截器
 */
public class DateFixInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String date = response.header("Date");
        //根据服务器时间修正本地时间
        if (date != null) {
            RetroDateUtil.setDateFix(date);
        }
        return response;
    }
}
