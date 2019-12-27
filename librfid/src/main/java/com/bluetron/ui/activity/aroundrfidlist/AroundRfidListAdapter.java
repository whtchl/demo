package com.bluetron.ui.activity.aroundrfidlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetron.core.bean.epc.EpcData;
import com.bluetron.core.bean.rfid.Rfid;
import com.bluetron.core.bean.scandevice.ScanDevice;
import com.bluetron.librfid.R;
import com.seuic.uhf.EPC;

import java.util.ArrayList;
import java.util.List;

public class AroundRfidListAdapter extends RecyclerView.Adapter<MyAroundRfidListViewHolder> {
    private List<EpcData> epcData = new ArrayList<EpcData>();
    private Context context;

    public AroundRfidListAdapter(List<EpcData> list, Context context) {
        this.context = context;
        this.epcData = list;
    }

    public AroundRfidListAdapter( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyAroundRfidListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_around_rfid,viewGroup,false);
        MyAroundRfidListViewHolder holder = new MyAroundRfidListViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyAroundRfidListViewHolder viewHolder, int i) {
        //Toast.makeText(context,epcData.get(i).getEpc().getId()+"@@",Toast.LENGTH_SHORT).show();

        viewHolder.tvItemAroundRfidUuid.setText(epcData.get(i).getEpc().getId()+"");
        viewHolder.tvItemAroundRfidRssi.setText(epcData.get(i).getEpc().rssi+"");
        viewHolder.tvItemAroundRfidData.setText(epcData.get(i).getUserData()+"");
    }

    @Override
    public int getItemCount() {
        return epcData.size();
    }
    public void updateEpcDataList(List<EpcData> list) {

        epcData.clear();
        epcData.addAll(list);

        //if(epcData != null)
        //Toast.makeText(context,epcData.size()+"&",Toast.LENGTH_SHORT).show();
    }
}

class MyAroundRfidListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemAroundRfidUuid,tvItemAroundRfidData,tvItemAroundRfidRssi;
    public RelativeLayout rlItemAroundRfid;
    public MyAroundRfidListViewHolder(View itemView) {
        super(itemView);
        tvItemAroundRfidRssi = itemView.findViewById(R.id.tv_item_around_rfid_rssi);
        tvItemAroundRfidUuid = itemView.findViewById(R.id.tv_item_around_rfid_uuid);
        tvItemAroundRfidData = itemView.findViewById(R.id.tv_item_around_rfid_data);
        rlItemAroundRfid = itemView.findViewById(R.id.rl_item_around_rfid);
    }
}
