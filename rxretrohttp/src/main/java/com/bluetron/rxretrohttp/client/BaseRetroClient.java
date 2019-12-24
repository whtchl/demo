package com.bluetron.rxretrohttp.client;

import com.bluetron.rxretrohttp.RxRetroHttp;
import com.bluetron.rxretrohttp.converter.RetroGsonConverterFactory;
import com.bluetron.rxretrohttp.func.RetryExceptionFunc;
import com.bluetron.rxretrohttp.util.RxSchedulerUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableTransformer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author tongxb
 * @date 2018/3/20
 * 请求客户端抽象类
 */
public abstract class BaseRetroClient<Client extends BaseRetroClient> {
    private String mBaseUrl;//请求url
    private int mRetryCount;//重试次数
    private int mRetryDelay;//延迟重试
    private int mRetryIncreaseDelay;//叠加延迟
    private boolean mIsDebug;//是否为debug模式
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private final List<Interceptor> networkInterceptors = new ArrayList<>();
    private final List<Interceptor> interceptors = new ArrayList<>();
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private Retrofit.Builder mRetrofitBuilder;

    /**
     * 初始化并获取各网络请求配置
     */
    public BaseRetroClient() {
        mBaseUrl = RxRetroHttp.getBaseUrl();
        mRetryCount = RxRetroHttp.getRetryCount();
        mRetryDelay = RxRetroHttp.getRetryDelay();
        mRetryIncreaseDelay = RxRetroHttp.getRetryIncreaseDelay();
        mIsDebug = RxRetroHttp.isDebug();
        generateOkClient();
        generateRetrofit();
    }

    /**
     * 配置OkHttp
     */
    private void generateOkClient() {
        mOkHttpClientBuilder = RxRetroHttp.getOkHttpClient().newBuilder();
        for (Interceptor interceptor : interceptors) {
            mOkHttpClientBuilder.addInterceptor(interceptor);
        }
        if (networkInterceptors.size() > 0) {
            for (Interceptor interceptor : networkInterceptors) {
                mOkHttpClientBuilder.addNetworkInterceptor(interceptor);
            }
        }
        if (mIsDebug) {
            mOkHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
    }

    /**
     * 配置Retrofit
     */
    private void generateRetrofit() {
        mRetrofitBuilder = RxRetroHttp.getRetrofit().newBuilder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(RetroGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 构建请求客户端
     */
    public Client build() {
        mOkHttpClient = mOkHttpClientBuilder.build();
        mRetrofitBuilder.client(mOkHttpClient);
        mRetrofit = mRetrofitBuilder.build();
        return (Client) this;
    }

    /**
     * 构建Retrofit请求服务
     */
    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    /**
     * 配置RxJava线程切换以及重试机制
     */
    public <T> ObservableTransformer<T, T> composeApi() {
        return observable -> observable
                .compose(RxSchedulerUtil.apiIoToMain())
                .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay));
    }
}
