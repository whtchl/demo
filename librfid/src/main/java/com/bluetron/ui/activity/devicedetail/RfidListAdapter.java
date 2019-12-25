package com.bluetron.ui.activity.devicedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bluetron.core.bean.rfid.Rfid;
import com.bluetron.librfid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * project name MultipleText
 * package name com.text.multipletext
 * Create by lxg
 * on 2017/9/7
 * at 14:53
 */
public class RfidListAdapter extends RecyclerView.Adapter<RfidListPopViewHolder> {

    private Context context;
    private List<Rfid> listBean;
    public Map<String, Object> maps;

    public RfidListAdapter(Context context, List<Rfid> listBean) {
        this.context = context;
        this.listBean = listBean;
        //initData();//初始化
    }

    /*private void initData() {
        maps = new HashMap<>();
        for (int i = 0; i < listBean.size(); i++) {
            maps.put(listBean.get(i).getName(), listBean.get(i));
        }
    }*/


    @Override
    public RfidListPopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RfidListPopViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_rfid, parent, false));
    }

    @Override
    public void onBindViewHolder(RfidListPopViewHolder holder, final int position) {
         holder.tvRfidItem.setText(listBean.get(position).getUuid());
         holder.tvRfidItem.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View view) {
                 Log.i("tchl","rifd");
             }
         });
    }


    @Override
    public int getItemCount() {
        return listBean.size();
    }
}
