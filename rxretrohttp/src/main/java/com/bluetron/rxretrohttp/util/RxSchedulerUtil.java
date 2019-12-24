package com.bluetron.rxretrohttp.util;

import com.bluetron.rxretrohttp.func.ExceptionHandleFunc;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author tongxb
 * @date 2018/3/22
 */

public class RxSchedulerUtil {
    public static <T> ObservableTransformer<T, T> apiIoToMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new ExceptionHandleFunc<T>());
    }

    public static <T> ObservableTransformer<T, T> ioToMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
