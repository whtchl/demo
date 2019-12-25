package com.bluetron.suplink.rfid;

import android.support.multidex.MultiDexApplication;

import com.bluetron.rxretrohttp.RxRetroHttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @auther tongxb
 * @data 2019-12-25
 */
public class MApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RxRetroHttp.getInstance().resetInterceptor()
                .setBaseUrl("http://192.168.8.81:8042")
//                .generateRetroClient("rfid")
                .setTimeOut(15000)
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new TokenInvalidInterceptor())
//                .addInterceptor(new ChangeBaseUrlInterceptor())
                .setHostnameVerifier2()
                .setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .setDebug(BuildConfig.DEBUG)
                .init(this);
    }
}
