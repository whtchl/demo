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
import com.seuic.uhf.EPC;

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
    private List<EPC> listBean;
    public Map<String, Object> maps;
    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener1) {
        this.listener = listener1;
    }

    public void updateList(List<EPC> list){
        listBean.clear();
        listBean = list;
    }
    public RfidListAdapter(Context context, List<EPC> listBean1) {
        this.context = context;
        this.listBean = listBean1;
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

        View view=View.inflate(context,R.layout.item_rfid,null);
        RfidListPopViewHolder holder=new RfidListPopViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,(int)v.getTag());
                }
            });


            return holder;
        /*return new RfidListPopViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_rfid, parent, false));*/
    }

    @Override
    public void onBindViewHolder(RfidListPopViewHolder holder, final int position) {
        holder.tvRfidItem.setText(listBean.get(position).getId());
        holder.itemView.setTag(position);
        /*holder.tvRfidItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onItemClick(listBean.get(position));

                Log.i("tchl", "rifd");
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return listBean.size();
    }

    /*public interface OnItemClickListener {
        void onItemClick(EPC epc);
    }*/

    /*private RfidListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(RfidListAdapter.OnItemClickListener mOnItemClickListener1) {
        this.mOnItemClickListener = mOnItemClickListener1;
    }*/

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}