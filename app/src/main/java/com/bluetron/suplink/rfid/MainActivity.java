package com.bluetron.suplink.rfid;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.bluetron.core.bean.rfid.Rfid;
import com.bluetron.ui.activity.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Rfid> rfidList = new ArrayList<Rfid>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTaskListActivity();
        finish();
    }
    private void startMainActivity(){
        Intent intent = new Intent(this, com.bluetron.ui.activity.devicedetail.DeviceDetailActivity.class);
        startActivity(intent);
    }

    private void startTaskListActivity(){
        Intent intent = new Intent(this, com.bluetron.ui.activity.TaskListActivity.class);
        startActivity(intent);
    }
   /* private void startDeviceDetailActivity(){
        Intent intent = new Intent(this, DeviceDetailActivityDel.class);
        startActivity(intent);
    }*/
    private void startTaskDetailActivity(){
        Intent intent = new Intent(this, TaskDetailActivity.class);
        startActivity(intent);
    }
}
