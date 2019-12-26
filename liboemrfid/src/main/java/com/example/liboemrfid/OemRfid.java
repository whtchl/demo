package com.example.liboemrfid;

import android.app.Application;
import android.content.Context;

import com.seuic.uhf.EPC;

import java.util.List;

public abstract class OemRfid {
    private static OemRfid instance;
    public static synchronized OemRfid initialize(Context context , int oemType) {
        /*ParamsAppToWebView paramsAppToWebView = new ParamsAppToWebView();
        paramsAppToWebView.setServerAddress(ServerAdress);
        paramsAppToWebView.setSuposToken(SuposToken);
        paramsAppToWebView.setUserName(userName);
        paramsAppToWebView.setZhizhiToken(SuposToken);*/
        return instance = RealOemRfid.create(context,oemType);
    }

    public static synchronized OemRfid client()  {
        if (instance == null) {
            throw new IllegalStateException("Please call SLCoreSdk.initialize() before requesting the client.");
        }
        return instance;
    }

    public abstract Boolean openRfid();

    public abstract Boolean continueScanRfid();

    public abstract Boolean stopScanRfid();

    public abstract void closeRfid();

    public abstract List<EPC> readRfid();

    public abstract Boolean writeRfid(byte[] Epc, byte[] PassWord, int Bank, int Offset ,int Len, byte[] Data);

}
