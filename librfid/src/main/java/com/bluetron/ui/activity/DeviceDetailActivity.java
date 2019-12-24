package com.bluetron.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bluetron.librfid.R;
import com.bluetron.ui.activity.popupwidnow.RfidItemListPopupWindow;
import com.bluetron.ui.activity.popupwidnow.RfidListPopupWindow;

public class DeviceDetailActivity extends AppCompatActivity {
    Button btnDeviceWriteRfid;
    //RfidListPopupWindow mRfidListPopupWindow;
    RfidItemListPopupWindow rfidItemListPopupWindow;
    Context mContext;
    final String itmes[]={"第一个子项","第二个子项","第三个子项"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        initView();
        //initParams();
        initEvent();
    }


    private void initView() {
        btnDeviceWriteRfid = findViewById(R.id.btn_device_write_rfid);
        rfidItemListPopupWindow = new RfidItemListPopupWindow(getApplicationContext());

    }
    /*private void initParams() {
        //mRfidListPopupWindow=new RfidListPopupWindow(this);
        mContext = this;
    }*/

    private void initEvent() {
        btnDeviceWriteRfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceWriteRfid();
               /* initListPopupWindow();
                mRfidListPopupWindow.setAnchorView(view);
                mRfidListPopupWindow.show();*/
            }
        });




    }

    /*private void initListPopupWindow(){
        mRfidListPopupWindow.setAdapter(new ArrayAdapter<String>(this,R.layout.item_rfid, itmes));
        mRfidListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(mContext, "点击了"+itmes[position], Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    private void deviceWriteRfid() {
        rfidItemListPopupWindow.showPopupWindow();
    }
}
