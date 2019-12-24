package com.bluetron.ui.activity.popupwidnow;

import android.content.Context;
import android.view.View;

import com.bluetron.librfid.R;

import razerdp.basepopup.BasePopupWindow;

public class RfidItemListPopupWindow extends BasePopupWindow {
    public RfidItemListPopupWindow(Context context){
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return  createPopupById(R.layout.rfid_item_list);
    }
}
