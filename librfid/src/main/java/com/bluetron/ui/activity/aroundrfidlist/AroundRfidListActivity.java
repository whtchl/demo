package com.bluetron.ui.activity.aroundrfidlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.librfid.R;
import com.bluetron.router.PathConstants;

@Route(path = PathConstants.PATH_AROUND_RFID_LIST)
public class AroundRfidListActivity extends BaseTitleBackActivity {
    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("周围的RFID设备");
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_around_rfid_list;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_rfid_list);
    }*/
}
