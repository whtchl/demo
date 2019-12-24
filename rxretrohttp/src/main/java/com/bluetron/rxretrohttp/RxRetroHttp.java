package com.bluetron.rxretrohttp;

import android.annotation.SuppressLint;
import android.app.Application;

import com.bluetron.rxretrohttp.client.BaseRetroClient;
import com.bluetron.rxretrohttp.client.SimpleRetroClient;
import com.bluetron.rxretrohttp.https.HttpsUtils;
import com.bluetron.rxretrohttp.interceptors.DateFixInterceptor;
import com.facebook.stetho.Stetho;

import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.ObservableTransformer;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author tongxb
 * @date 2018/3/19
 * Rx+Retrofit网络请求配置器
 */
public class RxRetroHttp {

    @SuppressLint("StaticFieldLeak")
    public static Application sApplication;
    @SuppressLint("StaticFieldLeak")
    private static volatile RxRetroHttp INSTANCE;

    private static final int DEFAULT_TIMEOUT = 5000;//默认的超时时间
    private static final int DEFAULT_RETRY_COUNT = 0;//默认重试次数
    private static final int DEFAULT_RETRY_INCREASE_DELAY = 0;//默认重试叠加时间
    private static final int DEFAULT_RETRY_DELAY = 500;//默认重试延时

    private String mBaseUrl;//全局请求url
    private int mTimeOut = DEFAULT_TIMEOUT;//超时时间
    private int mRetryCount = DEFAULT_RETRY_COUNT;//重试次数默认3次
    private int mRetryDelay = DEFAULT_RETRY_DELAY;//默认延迟500毫秒重试
    private int mRetryIncreaseDelay = DEFAULT_RETRY_INCREASE_DELAY;//默认叠加延迟0毫秒
    private OkHttpClient.Builder mOkHttpClientBuilder;//okHttp请求的客户端
    private Retrofit.Builder mRetrofitBuilder;//Retrofit请求Builder
    private BaseRetroClient mCommonRetroClient;
    private Map<String, BaseRetroClient> mRetroClientMap = new HashMap<>();
    private boolean mIsDebug = false;

    private RxRetroHttp() {
        generateOkHttpBuilder();
        generateRetrofitBuilder();
    }

    private void generateOkHttpBuilder() {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mOkHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(new DateFixInterceptor());
    }

    private void generateRetrofitBuilder() {
        mRetrofitBuilder = new Retrofit.Builder();

    }

    public static RxRetroHttp getInstance() {
        if (INSTANCE == null) {
            synchronized (RxRetroHttp.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RxRetroHttp();
                }
            }
        }
        return INSTANCE;
    }

    public static OkHttpClient getOkHttpClient() {
        return getInstance().mOkHttpClientBuilder.build();
    }

    public static Retrofit getRetrofit() {
        return getInstance().mRetrofitBuilder.build();
    }

    /**
     * 对外暴露 OkHttpClient,方便自定义
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return getInstance().mOkHttpClientBuilder;
    }

    /**
     * 对外暴露 Retrofit,方便自定义
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        return getInstance().mRetrofitBuilder;
    }

    /**
     * https的全局访问规则
     */
    public RxRetroHttp setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        mOkHttpClientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }

    public RxRetroHttp setHostnameVerifier2() {
        SSLContext sslContext = null;
        javax.net.ssl.SSLSocketFactory sslSocketFactory = null;
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        mOkHttpClientBuilder.sslSocketFactory(sslSocketFactory);
        return this;
    }

    /**
     * https的全局自签名证书
     */
    public RxRetroHttp setCertificates(InputStream... certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * https双向认证证书
     */
    public RxRetroHttp setCertificates(InputStream bksFile, String password, InputStream...
            certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(bksFile, password,
                certificates);
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    public RxRetroHttp setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        mOkHttpClientBuilder.hostnameVerifier(new UnSafeHostnameVerifier(mBaseUrl));
        mRetrofitBuilder.baseUrl(mBaseUrl);
        return this;
    }

    public RxRetroHttp setTimeOut(int timeOut) {
        this.mTimeOut = timeOut;
        mOkHttpClientBuilder.connectTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .writeTimeout(mTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }

    public RxRetroHttp addInterceptor(Interceptor interceptor) {
        mOkHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    public RxRetroHttp addNetworkInterceptor(Interceptor interceptor) {
        mOkHttpClientBuilder.addNetworkInterceptor(interceptor);
        return this;
    }

    public RxRetroHttp resetInterceptor() {
        mOkHttpClientBuilder.interceptors().clear();
        mOkHttpClientBuilder.networkInterceptors().clear();
        return this;
    }

    public RxRetroHttp setDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    public static String getBaseUrl() {
        return getInstance().mBaseUrl;
    }

    public static int getRetryCount() {
        return getInstance().mRetryCount;
    }

    public static int getRetryDelay() {
        return getInstance().mRetryDelay;
    }

    public static int getRetryIncreaseDelay() {
        return getInstance().mRetryIncreaseDelay;
    }

    public static boolean isDebug() {
        return getInstance().mIsDebug;
    }

    private static class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        UnSafeHostnameVerifier(String host) {
            this.host = host;
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return !(this.host == null || "".equals(this.host) || !this.host.contains(hostname));
        }
    }

    public static <T> T create(Class<T> cls) {
        return (T) getInstance().mCommonRetroClient.create(cls);
    }

    /**
     * 为处理多url情况，根据tag绑定Retrofit服务
     */
    public static <T> T create(Class<T> cls, String tag) {
        BaseRetroClient retroClient = getInstance().getRetroClient(tag);
        if (retroClient == null) {
            return null;
        }
        return (T) retroClient.create(cls);
    }

    public static <T> ObservableTransformer<T, T> composeApi() {
        return getInstance().mCommonRetroClient.composeApi();
    }

    public static <T> ObservableTransformer<T, T> composeApi(String tag) {
        BaseRetroClient retroClient = getInstance().getRetroClient(tag);
        if (retroClient == null) {
            return null;
        }
        return retroClient.composeApi();
    }

    public static void createRetroClient(String tag) {
        getInstance().getRetroClient(tag);
    }

    public void init(Application app) {
        RxRetroHttp.sApplication = app;
        if (mIsDebug) {
            Stetho.initializeWithDefaults(app);
        }
        mCommonRetroClient = new SimpleRetroClient().build();
    }

    public void generateRetroClient(String tag) {
        SimpleRetroClient retroClient = new SimpleRetroClient().build();
        mRetroClientMap.put(tag, retroClient);
    }

    private BaseRetroClient getRetroClient(String tag) {
        return mRetroClientMap.get(tag);
    }

    public static Application getApp() {
        return RxRetroHttp.sApplication;
    }
}
