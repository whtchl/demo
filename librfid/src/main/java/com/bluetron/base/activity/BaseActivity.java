package com.bluetron.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bluetron.librfid.R;
import com.bluetron.rxretrohttp.interfaces.IBaseApiAction;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * @author tongxb
 * @date 2018/3/23
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseApiAction {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            if (shouldBindEvent()) {
                EventBus.getDefault().register(this);
            }
            initVariables();
            initViews();
        } else {
            throw new IllegalArgumentException("You must set a layoutID for activity first!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shouldBindEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 初始化view
     */
    protected abstract void initViews();

    /**
     * 初始化和Bundle无关的变量
     */
    protected abstract void initVariables();

    /**
     * 获取layoutID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 是否需要绑定EventBus
     *
     * @return
     */
    protected boolean shouldBindEvent() {
        return false;
    }



    //国际化
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
//
//    }

}
