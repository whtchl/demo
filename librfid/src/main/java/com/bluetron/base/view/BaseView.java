package com.bluetron.base.view;

import com.bluetron.rxretrohttp.interfaces.IBaseApiAction;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @auther tongxb
 * @data 2019-12-23
 */
public interface BaseView extends IBaseApiAction {

    <T> LifecycleTransformer<T> getLifecycleTransformer();
}
