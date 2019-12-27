package com.bluetron.ui.activity.rfiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;
import com.bluetron.router.PathConstants;
import com.example.liboemrfid.OemRfid;
import com.example.liboemrfid.OemType;
import com.seuic.uhf.UHFService;
/*http://106.15.197.181:8888/swagger-ui.html#/controller/tasksUsingGET*/
/**
 * @auther tongxb
 * @data 2019-12-23
 */
@Route(path = PathConstants.PATH_RFID_DEMO)
public class RfidDemoActivity extends BaseTitleBackActivity {

    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.GONE);
        setTitleTxt("RFID DEMO");

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {
                return R.layout.activity_rfid_demo;
    }
    //下载任务
    public void OnClickXzrw(View v) {
        Navigation.navigateToTaskList();
    }
    //写入标签
    public void OnClickXrbq(View v) {
        Navigation.navigateToAroundRfidList();
    }

    //我的任务
    public void OnClickWdrw(View v) {
        Navigation.navigateToTaskList();
    }
    //设备明细
    public void OnClickSbmx(View v) {
        //Navigation.navigateToTaskList();
        Navigation.navigateDeviceList();
    }

}
