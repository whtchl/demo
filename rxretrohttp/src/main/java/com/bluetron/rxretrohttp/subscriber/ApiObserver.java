package com.bluetron.rxretrohttp.subscriber;

import com.bluetron.rxretrohttp.interfaces.IBaseApiAction;

import io.reactivex.observers.DisposableObserver;

/**
 * @author tongxb
 * @date 2017/11/29
 * API统一处理
 */
public abstract class ApiObserver<T> extends DisposableObserver<T> {
    private ApiActionDelegate<T> mApiActionDelegate;

    public ApiObserver() {
        this(null);
    }

    public ApiObserver(IBaseApiAction apiAction) {
        this(apiAction, apiAction != null, apiAction != null);
    }

    public ApiObserver(IBaseApiAction apiAction, boolean isShowLoading, boolean isShowMsg) {
        mApiActionDelegate = new ApiActionDelegate<>(apiAction, isShowLoading, isShowMsg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiActionDelegate.onStart();
    }

    @Override
    public void onNext(T data) {
        success(data);
        mApiActionDelegate.onNext(data);
    }

    @Override
    public void onError(Throwable t) {
        mApiActionDelegate.onError(t);
        error(t);
    }

    @Override
    public void onComplete() {
        mApiActionDelegate.onComplete();
        complete();
    }

    protected abstract void success(T data);

    protected void error(Throwable t) {

    }

    protected void complete() {

    }
}
