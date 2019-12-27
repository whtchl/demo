package com.bluetron.ui.activity.aroundrfidlist;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.core.bean.epc.EpcData;
import com.bluetron.core.bean.rfid.Rfid;
import com.bluetron.core.bean.scandevice.ScanDevice;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.PathConstants;
import com.bluetron.ui.activity.popupwindow.PopupWindowLoading;
import com.bluetron.ui.activity.taskdetail.TaskDetailAdapter;
import com.example.liboemrfid.OemRfid;
import com.example.liboemrfid.OemType;
import com.example.liboemrfid.seuic.BaseUtil;
import com.seuic.uhf.EPC;

import java.util.ArrayList;
import java.util.List;

@Route(path = PathConstants.PATH_AROUND_RFID_LIST)
public class AroundRfidListActivity extends BaseTitleBackActivity {
    Button btnScan;
    TextView tvRfidTotal;
    RecyclerView recyclerView;
    TaskDetailAdapter Adapter;
    //ArrayList<E> aroundRfidList = new ArrayList<Rfid>();
    AroundRfidListAdapter aroundRfidListAdapter;
    private List<EPC> mEPCList;
    private List<EpcData> epcDataList;
    static int m_count = 0;
    private Thread mInventoryThread;
    private InventoryRunable mInventoryRunable;
    public boolean mInventoryStart = false;
    Context context;
    String testtemp="";
    PopupWindowLoading popupWindowLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // new object
        OemRfid.initialize(this, OemType.SECUIC);
        OemRfid.client();
        // open UHF
        boolean ret =  OemRfid.client().openRfid();

    }
    @Override
    protected void onPause() {
        super.onPause();
        OemRfid.client().closeRfid();
    }

    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("周围的RFID设备");
        btnScan = findViewById(R.id.btn_around_rfid_list_scan);
        tvRfidTotal = findViewById(R.id.tv_rfid_total);
        recyclerView = findViewById(R.id.recycle_view_around_rfid_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mEPCList = new ArrayList<EPC>();
        epcDataList = new ArrayList<EpcData>();
        popupWindowLoading = new PopupWindowLoading(this);
    }

    @Override
    protected void initVariables() {
        /*EPC epc = new EPC();
        epc.rssi = 223;
        for(int i =0; i<3;i++){
            //mEPCList.add(epc);
            epcDataList.add(new EpcData(new EPC(),"123"));
        }*/
        aroundRfidListAdapter = new AroundRfidListAdapter(this);
        mInventoryRunable = new InventoryRunable();
        recyclerView.setAdapter(aroundRfidListAdapter);
        aroundRfidListAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_around_rfid_list;
    }

    public void OnClickStartStopScan(View v) {
        if(btnScan.getText().equals("开始扫描")){
            ScanRfidContinue();
        }else if(btnScan.getText().equals("停止扫描")){
            ReadRfidStop();
        }
    }

    public void OnClickReadRfidListData(View v) {
        if(btnScan.getText().equals("停止扫描")){
            Toast.makeText(this,"请先停止扫描后读取数据",Toast.LENGTH_LONG).show();
        }else if(epcDataList == null || (epcDataList!=null && epcDataList.size() == 0)){
            Toast.makeText(this,"没有扫描到数据，不能进行读取Rfid操作",Toast.LENGTH_LONG).show();
        }else{
            ReadRfidUserData();
        }

    }


    private void epcListToEpcDataList(){
        epcDataList.clear();
        if(mEPCList !=null ){//&& epcDataList != null
            for(int i=0; i<mEPCList.size();i++){

                epcDataList.add(new EpcData(mEPCList.get(i),"12333"));

            }
            testtemp = testtemp + epcDataList.size()+" ";
            //if(epcDataList != null)
            //Toast.makeText(context,epcDataList.size()+"&",Toast.LENGTH_SHORT).show();
        }
    }

    private void ReadRfidUserData() {

        if(mEPCList != null){
            /*popupWindowLoading.showPopupWindow();
            popupWindowLoading.setOutSideDismiss(false);
            popupWindowLoading.setOutSideTouchable(false);*/
            epcDataList.clear();
            for(int i=0;i<mEPCList.size();i++){

                int bank = Integer.parseInt(OemType.SECUIC_BANK);
                int address = Integer.parseInt(OemType.SECUIC_ADDRESS);
                int length = Integer.parseInt(OemType.SECUIC_LEN);

                String str_password = OemType.SECUIC_PWD;

                byte[] Epc = mEPCList.get(i).id;

                byte[] btPassword = new byte[16];
                BaseUtil.getHexByteArray(str_password, btPassword, btPassword.length);
                byte[] buffer = new byte[OemType.SECUIC_MAX_LEN];
                if (length > OemType.SECUIC_MAX_LEN) {
                    buffer = new byte[length];
                }

                String data="";
                if (!OemRfid.client().RfidreadTagData(Epc, btPassword, bank, address, length, buffer)) {

                    Toast.makeText(context, mEPCList.get(i).getId()+":读取Rfid数据失败", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getActivity(), R.string.readTagData_sucess, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "读取Rfid数据成功", Toast.LENGTH_SHORT).show();
                    data = BaseUtil.getHexString(buffer, length, " ");
                }
                epcDataList.add(new EpcData(mEPCList.get(i),data));
            }

            aroundRfidListAdapter.updateEpcDataList(epcDataList);
            aroundRfidListAdapter.notifyDataSetChanged();
            //popupWindowLoading.dismiss();

        }
    }

    private void ScanRfidContinue() {
        clearList();
        //mSelectedIndex = -1;
        aroundRfidListAdapter.notifyDataSetChanged();
        if (mInventoryThread != null && mInventoryThread.isAlive()) {
            System.out.println("Thread not null");
            return;
        }

        if (OemRfid.client().continueScanRfid()) {
            System.out.println("RfidInventoryStart sucess.");

            mInventoryStart = true;
            mInventoryThread = new Thread(mInventoryRunable);
            mInventoryThread.start();
            btnScan.setText("停止扫描");
            btnScan.setBackgroundColor(Color.parseColor("#808080"));

        } else {
            System.out.println("RfidInventoryStart faild.");
        }
        return;
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
    private void clearList() {
        if(epcDataList != null){
            epcDataList.clear();
        }
        if (mEPCList != null) {
            mEPCList.clear();
            aroundRfidListAdapter.notifyDataSetChanged();
            m_count = 0;
        }
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            // Refresh listview
            switch (msg.what) {
                case 1:
                    synchronized (context) {
                        mEPCList = OemRfid.client().readRfid();
                        epcListToEpcDataList();
                        //=ReadRfidUserData();
                    }
                    refreshData();
                    break;
                default:
                    break;
            }
        };
    };


    private void refreshData() {

        if (mEPCList != null && epcDataList != null) {
            // Gets the number inside the list of all labels
            int count = 0;
            for (EPC item : mEPCList) {
                count += item.count;
            }
            /*if (count > m_count) {
                playSound();
            }*/
            tvRfidTotal.setText(mEPCList.size()+"  "+epcDataList.size() +" !"+testtemp);
            aroundRfidListAdapter.updateEpcDataList(epcDataList);
            aroundRfidListAdapter.notifyDataSetChanged();

            m_count = count;
        }
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
}
