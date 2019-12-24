package com.bluetron.base.presenter;

import com.bluetron.base.view.BaseView;

/**
 * @auther tongxb
 * @data 2019-12-23
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(T view);
}
