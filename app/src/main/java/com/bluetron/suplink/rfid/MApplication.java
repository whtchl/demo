package com.bluetron.suplink.rfid;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
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

        ARouter.init(this);

        RxRetroHttp.getInstance().resetInterceptor()
                .setBaseUrl("http://106.15.197.181:8888")
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
