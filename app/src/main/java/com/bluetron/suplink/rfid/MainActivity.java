package com.bluetron.suplink.rfid;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.bluetron.ui.activity.DeviceDetailActivity;
import com.bluetron.ui.activity.TaskDetailActivity;
import com.bluetron.ui.activity.popupwidnow.RfidItemListPopupWindow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDeviceDetailActivity();
    }

    private void startDeviceDetailActivity(){
        Intent intent = new Intent(this, DeviceDetailActivity.class);
        startActivity(intent);
    }
    private void startTaskDetailActivity(){
        Intent intent = new Intent(this, TaskDetailActivity.class);
        startActivity(intent);
    }
}
