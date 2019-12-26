package com.bluetron.ui.activity.aroundrfidlist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.librfid.R;
import com.bluetron.router.PathConstants;

@Route(path = PathConstants.PATH_AROUND_RFID_LIST)
public class AroundRfidListActivity extends BaseTitleBackActivity {
    Button btnScan;
    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("周围的RFID设备");
        btnScan = findViewById(R.id.btn_around_rfid_list_scan);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_around_rfid_list;
    }

    public void OnClickStartStopScan(View v) {
        if(btnScan.getText().equals("开始扫描")){
            btnScan.setText("停止扫描");
            btnScan.setBackgroundColor(Color.parseColor("#808080"));
        }else if(btnScan.getText().equals("停止扫描")){
            btnScan.setText("开始扫描");
            btnScan.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
}
