package com.bluetron.ui.activity.devicedetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bluetron.librfid.R;

/**
 * project name MultipleText
 * package name com.text.multipletext
 * Create by lxg
 * on 2017/9/7
 * at 14:53
 */
public class RfidListPopViewHolder extends RecyclerView.ViewHolder {
    /**
     * 展示所有数据的ViewHolder
     */

    public TextView tvRfidItem;
    //public CheckBox cbPopItem;


    public RfidListPopViewHolder(View itemView) {
        super(itemView);
        tvRfidItem = (TextView) itemView.findViewById(R.id.tv_rfid_item);
    }
}
