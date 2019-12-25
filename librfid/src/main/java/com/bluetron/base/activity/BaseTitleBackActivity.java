package com.bluetron.base.activity;

import android.widget.TextView;

import com.bluetron.librfid.R;

/**
 * @author tongxb
 * @date 2018/5/9
 */

public abstract class BaseTitleBackActivity extends BaseBackActivity {
    protected TextView commonTvTitle;

    @Override
    protected void inflateBaseView() {
        super.inflateBaseView();
        commonTvTitle = findViewById(R.id.common_tv_title);
    }

    public void setTitleTxt(String title) {
        //commonTvTitle = findViewById(R.id.common_tv_title);
        commonTvTitle.setText(title);
    }

    public void setTitleTxt(int resId) {
        commonTvTitle.setText(resId);
    }

    public void setTitleVisibility(int visibility) {
        commonTvTitle.setVisibility(visibility);
    }
}
