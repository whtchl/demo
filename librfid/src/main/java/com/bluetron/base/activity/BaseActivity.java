package com.bluetron.base.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.bluetron.librfid.R;
import com.bluetron.rxretrohttp.interfaces.IBaseApiAction;
import com.example.liboemrfid.OemRfid;
import com.example.liboemrfid.OemType;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * @author tongxb
 * @date 2018/3/23
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseApiAction {

    public Dialog mLoadingDialog;//loading
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            if (shouldBindEvent()) {
                EventBus.getDefault().register(this);
            }
            initViews();
            initVariables();

            initLoadingDialog();
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

    private void initLoadingDialog() {
        View loadingView = LayoutInflater.from(this).inflate(getLayoutLoading(),
                findViewById(android.R.id.content), false);
        mLoadingDialog = new Dialog(this, getLoadingStyle());
        mLoadingDialog.setContentView(loadingView);
        if (mLoadingDialog.getWindow() != null) {
            mLoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mLoadingDialog.setCanceledOnTouchOutside(false);
    }

    public <T> LifecycleTransformer<T> getLifecycleTransformer() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }


    @Override
    public void showLoading() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() && !isFinishing() &&
                !isDestroyed()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && !isFinishing() &&
                !isDestroyed()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLong(msg);
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

    protected int getLayoutLoading() {
        return R.layout.common_layout_loading;
    }

    protected int getLoadingStyle() {
        return R.style.CustomDialogStyle;
    }

    //国际化
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
//
//    }

}
