package com.example.liboemrfid;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.seuic.uhf.EPC;
import com.seuic.uhf.UHFService;

import java.util.List;

public class RealOemRfid extends OemRfid {

    private int mOemType;
    private UHFService mDeviceSecuic;
    private Context mContext;

    static OemRfid create(Context context, int OemType1) {

        return new RealOemRfid(context, OemType1);
    }


    RealOemRfid(Context context, int OemType1) {
        mOemType = OemType1;
        mContext = context;
        if (mOemType == OemType.SECUIC) {
            mDeviceSecuic = UHFService.getInstance();
        }
    }

    @Override
    public Boolean openRfid() {
        Boolean ret = false;
        if (mOemType == OemType.SECUIC) {
            ret = mDeviceSecuic.open();
            if (!ret) {
                Toast.makeText(mContext, "打开Rfid失败", Toast.LENGTH_LONG).show();
            }
        }
        return ret;
    }

    @Override
    public Boolean continueScanRfid() {
        if (mOemType == OemType.SECUIC) {
            return  mDeviceSecuic.inventoryStart();
        }
        return false;
    }

    @Override
    public Boolean stopScanRfid() {
        if (mOemType == OemType.SECUIC) {
            return mDeviceSecuic.inventoryStop();
        }
        return false;
    }

    @Override
    public void closeRfid() {
        if (mOemType == OemType.SECUIC) {
            mDeviceSecuic.close();
        }
    }

    @Override
    public List<EPC> readRfid() {
        if (mOemType == OemType.SECUIC) {
            return mDeviceSecuic.getTagIDs();
        }
        return null;
    }

    @Override
    public Boolean writeRfid(byte[] Epc, byte[] PassWord, int Bank, int Offset ,int Len, byte[] Data) {
        if (mOemType == OemType.SECUIC) {
            return mDeviceSecuic.writeTagData(Epc, PassWord, Bank, Offset, Len, Data);
        }
        return false;
    }
}
