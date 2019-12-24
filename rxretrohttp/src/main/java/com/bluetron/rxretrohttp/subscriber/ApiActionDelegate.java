package com.bluetron.rxretrohttp.subscriber;

import com.bluetron.rxretrohttp.exception.ApiException;
import com.bluetron.rxretrohttp.interfaces.IBaseApiAction;

/**
 * @author tongxb
 * @date 2018/5/7
 * API处理代理
 */
public class  ApiActionDelegate<T> {
    private IBaseApiAction mApiAction;
    private boolean mIsShowLoading, mIsShowMsg;

    public ApiActionDelegate(IBaseApiAction apiAction, boolean isShowLoading, boolean isShowMsg) {
        this.mApiAction = apiAction;
        this.mIsShowLoading = isShowLoading;
        this.mIsShowMsg = isShowMsg;
    }

    protected void onStart() {
        if (mIsShowLoading) {
            mApiAction.showLoading();
        }
    }

    public void onNext(T data) {
        dismissLoading();
    }

    public void onError(Throwable t) {
        if (mIsShowMsg && t instanceof ApiException) {
            mApiAction.showToast(((ApiException) t).getDisplayMessage());
        }
        dismissLoading();
    }

    public void onComplete() {
        dismissLoading();
    }

    private void dismissLoading() {
        if (mIsShowLoading) {
            mApiAction.dismissLoading();
        }
    }
}
