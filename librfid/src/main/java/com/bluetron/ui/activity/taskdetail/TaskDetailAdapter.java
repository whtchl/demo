package com.bluetron.ui.activity.taskdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluetron.core.bean.scandevice.ScanDevice;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ScanDevice> scanDeviceArrayList;
    private Context context;

    public TaskDetailAdapter(ArrayList<ScanDevice> list, Context context) {
        this.context = context;
        this.scanDeviceArrayList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_taskdetail_device,viewGroup,false);
        MyTaskDetailViewHolder holder = new MyTaskDetailViewHolder(view);
        return holder;
    }


    public void updateTaskDetailEpcList(ArrayList<ScanDevice>  list){
        //scanDeviceArrayList.clear();
        scanDeviceArrayList = list;
    }

    public int getNumberofIndicatorTrue(){
        int temp=0;
        if(scanDeviceArrayList!=null){
            for(int i =0;i<scanDeviceArrayList.size();i++){
                if(scanDeviceArrayList.get(i).isIndicator()){
                    temp++;
                }
            }
        }
        return temp;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MyTaskDetailViewHolder) viewHolder).tvItemTaskName.setText(scanDeviceArrayList.get(i).getName());
        ((MyTaskDetailViewHolder) viewHolder).tvItemDeviceData.setText(scanDeviceArrayList.get(i).getId());
        if(scanDeviceArrayList.get(i).isIndicator()){
            ((MyTaskDetailViewHolder) viewHolder).ivIndicator.setVisibility(View.VISIBLE);
        }else{
            ((MyTaskDetailViewHolder) viewHolder).ivIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return scanDeviceArrayList.size();
    }

}

class MyTaskDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemTaskName,tvItemDeviceData;
    public ImageView ivIndicator;
    public LinearLayout ll;
    public MyTaskDetailViewHolder(View itemView) {
        super(itemView);
        tvItemTaskName = itemView.findViewById(R.id.tv_item_task_name);
        tvItemDeviceData = itemView.findViewById(R.id.tv_item_device_data);
        ivIndicator = itemView.findViewById(R.id.iv_indicator);
    }
}
