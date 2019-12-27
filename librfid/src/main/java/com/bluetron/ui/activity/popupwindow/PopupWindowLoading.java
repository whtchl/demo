package com.bluetron.ui.activity.popupwindow;

import android.content.Context;
import android.view.View;

import com.bluetron.librfid.R;


import razerdp.basepopup.BasePopupWindow;

public class PopupWindowLoading extends BasePopupWindow {

    public PopupWindowLoading(Context context){
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return  createPopupById(R.layout.popupwindow_loading);
    }
}
