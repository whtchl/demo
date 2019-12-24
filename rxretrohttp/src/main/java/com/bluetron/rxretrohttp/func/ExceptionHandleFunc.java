package com.bluetron.rxretrohttp.func;

import com.bluetron.rxretrohttp.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author tongxb
 * @date 2018/3/22
 * RX异常处理
 */
public class ExceptionHandleFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) {
        //统一转化为ApiException
        return Observable.error(ApiException.handleException(throwable));
    }
}
