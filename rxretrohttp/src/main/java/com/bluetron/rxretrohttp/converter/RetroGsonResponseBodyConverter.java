package com.bluetron.rxretrohttp.converter;

import android.text.TextUtils;

import com.bluetron.rxretrohttp.bean.ApiResult;
import com.bluetron.rxretrohttp.bean.NoneResponse;
import com.bluetron.rxretrohttp.exception.ServerException;
import com.facebook.stetho.common.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by tongxb on 2017/12/21.
 */

class RetroGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private Type type;

    RetroGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        //解析为ApiResult
        try {
            if (TextUtils.isEmpty(response)) {
                return (T) (new NoneResponse());

            } else {

                ApiResult<T> apiResult = gson.fromJson(response, new TypeToken<ApiResult<T>>() {
                }.getType());
                //如果返回错误则抛出
                if (apiResult.getCode() == ApiResult.CODE_ERROR) {
                    throw new ServerException(apiResult.getMessage(), apiResult.getCode());
                } else if (type.equals(ApiResult.class) || apiResult.getContent() == null) {
                    //如果需求类型为ApiResult本身（一般情况下为无具体返回内容，只关心成功与否），则强转
                    if (apiResult.getCode() == ApiResult.CODE_SUCCESS) {
                        return (T) apiResult;
                    }
                    throw new ServerException(apiResult.getMessage(), apiResult.getCode());
                }
                //获取"data"字段内的字符串，反序列化
                return gson.fromJson(((JsonObject) new JsonParser().parse(response)).get("content"), type);
            }
        } finally {
            value.close();
        }
    }
}
