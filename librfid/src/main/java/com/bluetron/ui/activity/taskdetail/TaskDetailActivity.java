package com.bluetron.ui.activity.taskdetail;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseBackActivity;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.scandevice.ScanDevice;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;
import com.bluetron.router.PathConstants;

import java.util.ArrayList;
import java.util.List;

@Route(path = PathConstants.PATH_TASK_DETAIL)
public class TaskDetailActivity extends BaseTitleBackActivity implements TaskContract.View{
    TextView tvTaskName,tvTaskNumber,tvTotal;
    Button btnScan,btnDoneTask;
    RecyclerView recyclerView;
    TaskDetailAdapter taskDetailAdapter;
    ArrayList<ScanDevice> scanDevices = new ArrayList<ScanDevice>();
    @Autowired
    TaskListResponse taskListResponse;
   /* @Autowired
    ArrayList<TaskListResponse.device> deviceList;*/
    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("任务详情");
        tvTaskName = findViewById(R.id.tv_task_name);
        tvTaskNumber = findViewById(R.id.tv_task_number);
        tvTotal = findViewById(R.id.tv_total);
        btnScan = findViewById(R.id.btn_scan);
        btnDoneTask = findViewById(R.id.btn_done_task);
        recyclerView = findViewById(R.id.recycle_view_device_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void initVariables() {
        if(taskListResponse != null){
            tvTaskName.setText(taskListResponse.getName());
            tvTaskNumber.setText("0");
            tvTotal.setText(taskListResponse.getList().size()+"");
            initScanDevices(taskListResponse.getList());
            taskDetailAdapter = new TaskDetailAdapter(scanDevices, this);
            recyclerView.setAdapter(taskDetailAdapter);
            taskDetailAdapter.notifyDataSetChanged();
        }

    }

    private void initScanDevices(ArrayList<TaskListResponse.device> list){
        if(list != null){
            scanDevices.clear();
            for(int i=0;i<list.size();i++){
                ScanDevice scanDevice = new ScanDevice();
                scanDevice.setId(list.get(i).getId());
                scanDevice.setName(list.get(i).getName());
                scanDevice.setLastModifyDate(list.get(i).getLastModifyDate());
                scanDevice.setIndicator(false);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);
                scanDevices.add(scanDevice);scanDevices.add(scanDevice);scanDevices.add(scanDevice);scanDevices.add(scanDevice);
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void onGetTaskList(List<TaskListResponse> response) {
        Log.i("tchl","size:"+response.size());
    }

    public void OnClickBeginStopScan(View v) {
        if(btnScan.getText().equals("开始扫描")){
            btnScan.setText("停止扫描");
            btnScan.setBackgroundColor(Color.parseColor("#808080"));
        }else if(btnScan.getText().equals("停止扫描")){
            btnScan.setText("开始扫描");
            btnScan.setBackgroundColor(Color.parseColor("#008000"));
        }
    }
}
