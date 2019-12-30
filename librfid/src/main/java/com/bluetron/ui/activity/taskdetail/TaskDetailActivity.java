package com.bluetron.ui.activity.taskdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

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
import com.bluetron.ui.activity.aroundrfidlist.AroundRfidListActivity;
import com.example.liboemrfid.OemRfid;
import com.seuic.uhf.EPC;

import java.util.ArrayList;
import java.util.HashMap;
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
    Context context;
    private Thread mInventoryThread;
    private InventoryRunable mInventoryRunable;
    public boolean mInventoryStart = false;
   private List<EPC> mEPCList;
   String testtemp = "~";

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

        context = this;
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
        mInventoryRunable = new InventoryRunable();

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
            /*btnScan.setText("停止扫描");
            btnScan.setBackgroundColor(Color.parseColor("#808080"));*/
            ScanRfidContinue();

        }else if(btnScan.getText().equals("停止扫描")){
           /* btnScan.setText("开始扫描");
            btnScan.setBackgroundColor(Color.parseColor("#008000"));*/
            ReadRfidStop();
        }
    }

    private void clearList() {
        /*if(epcDataList != null){
            epcDataList.clear();
        }*/
        if (mEPCList != null) {
            mEPCList.clear();
            taskDetailAdapter.notifyDataSetChanged();
        }
    }

    private void ReadRfidStop() {
        mInventoryStart = false;

        if (mInventoryThread != null) {
            mInventoryThread.interrupt();
            mInventoryThread = null;
        }
        System.out.println("begin stop!!");
        if (OemRfid.client().stopScanRfid()) {
            System.out.println("end stop!!");
            btnScan.setText("开始扫描");
            btnScan.setBackgroundColor(Color.parseColor("#008000"));
        } else {
            System.out.println("RfidInventoryStop faild.");
        }
        return;
    }

    private void ScanRfidContinue() {
        clearList();
        //mSelectedIndex = -1;
        taskDetailAdapter.notifyDataSetChanged();
        if (mInventoryThread != null && mInventoryThread.isAlive()) {
            System.out.println("Thread not null");
            return;
        }

        if (OemRfid.client().continueScanRfid()) {
            Toast.makeText(this,"开始扫描Rfid设备",Toast.LENGTH_LONG).show();
            System.out.println("RfidInventoryStart sucess.");
            tvTaskNumber.setText("@@"+testtemp);
            mInventoryStart = true;
            mInventoryThread = new Thread(mInventoryRunable);
            mInventoryThread.start();
            btnScan.setText("停止扫描");
            btnScan.setBackgroundColor(Color.parseColor("#808080"));

        } else {
            System.out.println("RfidInventoryStart faild.");
            Toast.makeText(this,"扫描Rfid设备失败",Toast.LENGTH_LONG).show();
        }
        return;
    }

    private class InventoryRunable implements Runnable {

        @Override
        public void run() {
            while (mInventoryStart) {
                Message message = Message.obtain();// Avoid repeated application of memory, reuse of information
                message.what = 1;
                handler.sendMessage(message);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            // Refresh listview
            switch (msg.what) {
                case 1:
                    synchronized (context) {
                        mEPCList = OemRfid.client().readRfid();
                        testtemp = testtemp + mEPCList.size()+" ";
                    }
                    refreshData();
                    break;
                default:
                    break;
            }
        };
    };


    private void refreshData() {
        tvTaskNumber.setText( " %"+testtemp);
        if (mEPCList != null && scanDevices !=null) {
            // Gets the number inside the list of all labels
            /*int count = 0;
            for (EPC item : mEPCList) {
                count += item.count;
            }*/
            /*if (count > m_count) {
                playSound();
            }*/
            //tvRfidTotal.setText(mEPCList.size());//+"  "+epcDataList.size() +" !"+testtemp

            for(int i =0; i<mEPCList.size(); i++){
                if(mEPCList.get(i).getId().contains("3400300833")){

                    for(int j=0; j<scanDevices.size(); j++){
                        if(scanDevices.get(j).getId().contains("1111")){
                            scanDevices.get(j).setIndicator(true);
                            scanDevices.get(j).setLastModifyDate(System.currentTimeMillis());
                        }
                    }
                }else{
                    if(mEPCList.get(i).getId().contains("30000048029C130")){
                        for(int j=0; j<scanDevices.size(); j++){
                            if(scanDevices.get(j).getId().contains("222")){
                                scanDevices.get(j).setIndicator(true);
                                scanDevices.get(j).setLastModifyDate(System.currentTimeMillis());
                            }
                        }
                    }
                }
            }
            tvTaskNumber.setText(mEPCList.size()+ " !"+testtemp);//+"  "+epcDataList.size() +" !"+testtemp
            taskDetailAdapter.updateTaskDetailEpcList(scanDevices);
            taskDetailAdapter.notifyDataSetChanged();
        }
    }
}
