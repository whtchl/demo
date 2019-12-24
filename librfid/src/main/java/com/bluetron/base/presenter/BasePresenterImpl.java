package com.bluetron.base.presenter;

import com.bluetron.base.view.BaseView;

/**
 * @auther tongxb
 * @data 2019-12-23
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {


    protected T mView;

    @Override
    public void attachView(T mView) {
        this.mView = mView;
    }
}
