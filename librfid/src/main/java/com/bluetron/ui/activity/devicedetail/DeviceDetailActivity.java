package com.bluetron.ui.activity.devicedetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.core.bean.epc.EpcData;
import com.bluetron.core.bean.rfid.Rfid;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;
import com.bluetron.router.PathConstants;
import com.bluetron.ui.activity.aroundrfidlist.AroundRfidListActivity;
import com.bluetron.ui.activity.aroundrfidlist.AroundRfidListAdapter;
import com.bluetron.utils.TUtils;
import com.example.liboemrfid.OemRfid;
import com.example.liboemrfid.OemType;
import com.seuic.uhf.EPC;

import java.util.ArrayList;
import java.util.List;

@Route(path = PathConstants.PATH_DEVICE_DETAIL)
public class DeviceDetailActivity extends BaseTitleBackActivity {

    Button btnDeviceWriteRfid;
    private static final String TAG = "MainActivity";
    private LinearLayout llChose;
    private LinearLayout llShowChosed;
    private RecyclerView rvChosed;

    private View popView;
    private PopupWindow mPop;
    private RecyclerView rvPop;
    private RfidListAdapter rfidListAdapter;
    TextView tvDeviceName, tvDeviceId, tvId, tvDeviceTime;
    // private List<Rfid> listBean;

    //rfid list begin
    private Thread mInventoryThread;
    private InventoryRunable mInventoryRunable;
    public boolean mInventoryStart = false;
    Context context;
    private List<EPC> mEPCList;
    //rfid list end

    @Autowired
    TaskListResponse.device device;
  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        findViews();
        setPop();
        initView();
        initEvent();
    }*/

    @Override
    protected void initViews() {
        context = this;  //rfid list add
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt(device.getName());
        findViews();
        setPop();
        initView();
        initEvent();


    }

    @Override
    protected void initVariables() {
        if (device != null) {
            tvDeviceId.setText(device.getId());
            tvDeviceName.setText(device.getName());
            tvId.setText(device.getId());
            tvDeviceTime.setText(TUtils.longtoDateString(Long.valueOf(device.getLastModifyDate())));
        }
        mEPCList = new ArrayList<EPC>();
        mInventoryRunable = new InventoryRunable(); //rfid list add

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPop.setLayoutManager(layoutManager);
        rfidListAdapter = new RfidListAdapter(this, mEPCList);
        rvPop.setAdapter(rfidListAdapter);
        rfidListAdapter.setOnItemClickListener(new RfidListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(context,"dddddddddddd",Toast.LENGTH_LONG).show();
                ReadRfidStop(position);
            }
        });
        /*rfidListAdapter.setOnItemClickListener(new RfidListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context,"dddddddddddd",Toast.LENGTH_LONG).show();
            }
        });*/
        rfidListAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_device_detail;
    }

    /**
     * 初始化数据
     */
    private void initView() {
        //mEPCList = new ArrayList<EPC>();
        /**
         * RecyclerView垂直展示数据
         */
        // rvPop.setLayoutManager(new LinearLayoutManager(DeviceDetailActivity.this));
        /*for (int i = 0; i < 5; i++) {
            mEPCList.add(new Rfid("name" + i, "id" + i));
        }*/

        /**
         * 选中后展示数据的RecyclerView
         * 设置为Gridlayout形式，水平展示四个，然后依次垂直展示
         */
        /*rvChosed.setLayoutManager(new GridLayoutManager(DeviceDetailActivity.this, 4,
                LinearLayoutManager.VERTICAL, false));*/

    }


    /**
     * 点击事件
     */
    private void initEvent() {
        btnDeviceWriteRfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPop.showAsDropDown(v);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                mPop.showAtLocation(DeviceDetailActivity.this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                //展示全部数据
                //rfidListAdapter = new RfidListAdapter(DeviceDetailActivity.this, mEPCList);
                //rvPop.setAdapter(rfidListAdapter);

                ScanRfidContinue();
            }
        });


    }


    private void findViews() {
        rvChosed = (RecyclerView) findViewById(R.id.rv_chosed);
        btnDeviceWriteRfid = findViewById(R.id.btn_device_write_rfid);
        tvDeviceId = findViewById(R.id.tv_device_id);
        tvDeviceName = findViewById(R.id.tv_device_name);
        tvId = findViewById(R.id.tv_id);
        tvDeviceTime = findViewById(R.id.tv_device_time);
    }

    /**
     * 展示popuwindow
     */
    private void setPop() {
        popView = LayoutInflater.from(DeviceDetailActivity.this)
                .inflate(R.layout.rfid_item_list, null, false);
        mPop = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        mPop.setBackgroundDrawable(dw);

        mPop.setTouchable(true);
        mPop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mPop.setOutsideTouchable(true);

        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        rvPop = (RecyclerView) popView.findViewById(R.id.recycle_view_rfid_list);
        //tvPopSubmit = (TextView) popView.findViewById(R.id.tv_submit_pop);
    }

    private List<Rfid> rfidList = new ArrayList<Rfid>();

    //rfid list begin
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            // Refresh listview
            switch (msg.what) {
                case 1:
                    synchronized (context) {
                        mEPCList = OemRfid.client().readRfid();
                        //epcListToEpcDataList();
                        //=ReadRfidUserData();
                    }
                    refreshData();
                    break;
                default:
                    break;
            }
        }

        ;
    };


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


    private void refreshData() {

        if (mEPCList != null) {
            // Gets the number inside the list of all labels
            /*for (EPC item : mEPCList) {
                count += item.count;
            }*/
            //tvRfidTotal.setText(mEPCList.size()+"  "+epcDataList.size() +" !"+testtemp);
            /*aroundRfidListAdapter.updateEpcDataList(epcDataList);
            aroundRfidListAdapter.notifyDataSetChanged();*/
            //rfidListAdapter = new RfidListAdapter(DeviceDetailActivity.this, mEPCList);
            rfidListAdapter.updateList(mEPCList);
            rvPop.setAdapter(rfidListAdapter);
            rfidListAdapter.notifyDataSetChanged();
        }
    }

    private void ReadRfidStop(int position) {
        mInventoryStart = false;
        //Toast.makeText(context, "dddddd", Toast.LENGTH_LONG).show();
        if (mInventoryThread != null) {
            mInventoryThread.interrupt();
            mInventoryThread = null;
        }
        System.out.println("begin stop!!");
        if (OemRfid.client().stopScanRfid()) {
            System.out.println("end stop!!");
            if(mEPCList != null){
                Navigation.navigateToWriteRfid(mEPCList.get(position).id,mEPCList.get(position).getId()); //,mEPCList.get(position)
            }
        } else {
            System.out.println("RfidInventoryStop faild.");
            if(mEPCList != null){
                Navigation.navigateToWriteRfid(mEPCList.get(position).id,mEPCList.get(position).getId()); ///*,mEPCList.get(position)*/
            }
        }
        return;
    }

    private void ScanRfidContinue() {
        clearList();
        //mSelectedIndex = -1;
        rfidListAdapter.notifyDataSetChanged();
        if (mInventoryThread != null && mInventoryThread.isAlive()) {
            System.out.println("Thread not null");
            return;
        }
        if (OemRfid.client().continueScanRfid()) {
            System.out.println("RfidInventoryStart sucess.");
            mInventoryStart = true;
            mInventoryThread = new Thread(mInventoryRunable);
            mInventoryThread.start();
        } else {
            System.out.println("RfidInventoryStart faild.");
            Toast.makeText(context, "Rfid扫描失败", Toast.LENGTH_LONG).show();
        }
        return;
    }

    private void clearList() {
        if (mEPCList != null) {
            mEPCList.clear();
            rfidListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //OemRfid.client().closeRfid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
     /*   // new object
        OemRfid.initialize(this, OemType.SECUIC);
        OemRfid.client();
        // open UHF
        boolean ret = OemRfid.client().openRfid();*/

    }

    public RfidListAdapter getRfidListAdapter() {
        return rfidListAdapter;
    }

    //rfid list end

}
