package com.bluetron.base.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetron.librfid.R;


public abstract class BaseBackActivity extends BaseActivity{
    protected ImageView commonIvBack;
    protected View commonHeaderRoot;
    protected TextView commonTvRight;
    protected ImageView commonIvRightIcon;

    protected void inflateBaseView() {
        commonIvBack = findViewById(R.id.common_iv_back);
        commonTvRight = findViewById(R.id.common_tv_right);
        commonIvBack.setOnClickListener(view -> back());
        commonHeaderRoot = findViewById(R.id.header_root);
        commonIvRightIcon = findViewById(R.id.common_iv_setting);
    }

    public void setBackVisibility(int visibility) {
        commonIvBack.setVisibility(visibility);
    }

    public void setRightIconVisibility(int visibility,int resourceId) {
        commonIvRightIcon.setVisibility(visibility);
        commonIvRightIcon.setImageResource(resourceId);
    }

    public void setRightVisibility(int visibility){
        commonTvRight.setVisibility(visibility);
    }

    void back() {
        onBackPressed();
    }

    public void setHeaderVisibility(int visibility) {
        commonHeaderRoot.setVisibility(visibility);
    }

}
